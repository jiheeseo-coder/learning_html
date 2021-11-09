import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.trident.operation.builtin.FilterNull;
import org.apache.storm.trident.operation.builtin.MapGet;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.testing.MemoryMapState;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.DRPCClient;

public class TridentWordCount {
    public static class Split extends BaseFunction{
        // 문장을 잡아 공백으로 분할하고 각 단어에 대한 튜플을 방출
        @Override
        public void execute(TridentTuple tuple, TridentCollector collector) {
            String sentence = tuple.getString(0);
            for(String word: sentence.split(" ")){
                //분할 & 방출
                collector.emit(new Values(word));
            }
        }
    }

    public static StormTopology bulidTopology(){
        FixedBatchSpout spout = new FixedBatchSpout(new Fields("sentence"), 3,
                new Values("the cow jumped over the moon"),
                new Values("the man went to the store and bought some candy"),
                new Values("four score and seven years ago"),
                new Values("how many apples can you eat"));
        spout.setCycle(true);

        TridentTopology topology = new TridentTopology();
        // trident 계산을 구성하기 위한 인터페이스를 노출하는 TridentTopology 개체 생성
        TridentState wordCounts = topology.newStream("spout1", spout).parallelismHint(16).each(new Fields("sentence"),
                // 입력 소스에서 읽는 토폴로지에 새 데이터 스트림을 생성하는 newStream, 입력소스: FixedBatchSpout, ex)Kestrel, Kafka같은 대기열
                //   브로커일 수 있음,
                // Trident는 Zookeeper의 각 입력 소스(소비한 것에 대한 메타데이터)에 대한 소량의 상태를 추적하고 여기에서
                //   "spout1"의 문자열은 Trident가 해당 메타데이터를 보관해야하는 Zookeeper의 노드를 지정
                // "sentence"라는 필드 하나를 포함하는 스트림을 내보냄.
                new Split(), new Fields("word"))
                // "sentence"라는 필드를 가져와 단어로 분할하여 스트림의 각 튜플에 Split 함수를 적용함
                // 나머지 토폴로지는 단어 수를 계산하고 결과를 지속적으로 저장함
                .groupBy(new Fields("word"))
                .persistentAggregate(new MemoryMapState.Factory(),new Count(), new Fields("count"))
                        // "word" 필드로 그룹화됨
                        // persistentAggregatr(): state of source에서 집계 결과를 저장하고 업데이트함
                        // if 기타 영구 저장소에 저장하려면 Memcached를 사용하면 됨
                        // PersistentAggregate에 의해 저장된 값은 스트림에서 배출한 모든 배치의 집계를 나타냄
                        // PersistentAggregate 메소드는 Stream을 TridentState 객체로 변환함
                        //      TridentState 개체는 모든 단어수를 나타냄.
                        //      TridentState 개체를 사용하여 계산의 분산쿼리 부분을 구현함

                            // Count Aggregator를 사용하여 지속적으로 집계함
                .parallelismHint(16);
        // 단어 수에 대해 대기 시간이 짧은 분산 쿼리를 구현함. 쿼리는 공백으로 구분된 단어 목록을 입력으로 사용하고
        // 해당 단어의 개수 합계를 반환함. 이러한 쿼리 -> 백그라운드에서 병렬 처리된다는 점을 제외하고는
        // 일반 RPC 처럼 호출됨
        topology.newDRPCStream("words").each(new Fields("args"), new Split(), new Fields("word"))
                // 동일한 TridentTopology 개체가 DRPC 스트림을 생성하는데 사용, 함수이름: "words"
                //      함수 이름: DRPCClient를 사용할 때 실행에 첫 번째 인수에 주어진 함수 이름에 해당
                // DRPC call: 요청을 다나태는 단일 튜플을 입력으로 사용하는 자체적인 작은 배치 작업으로 처리됨
                // 튜플에는 클라이언트가 제공한 인수를 포함하는 "args"라는 필드가 있음 -> 인수는 공백으로 구분된 단어 목록
                // Split함수: 요청에 대한 인구를 구성 단어로 분할하는데 사용됨
                .groupBy(new Fields("word"))
                // "word"별로 그룹화 됨
                .stateQuery(wordCounts, new Fields("word"), new MapGet(), new Fields("count"))
                // stateQuery 연산자: 토폴로지의 첫 부분이 생성한 TridentState 개체를 쿼리하는데 사용
                // stateQuery: state of source(토폴로지의 다른 부분에서 계산된 단어 수)와 해당 상태를 쿼리하는 함수 사용
                // 각 단어의 개수를 가져오는 MapGet 함수 호출됨
                // DRPC 스트림은 TridentState와 똑같은 방식으로 그룹화됨("word" 필터 기준) -> 각 단어 쿼리는 해당
                //   단어에 대한 업데이트를 관리하는 TridentState 개체의 정확한 파티션으로 라우팅됨
                .each(new Fields("count"), new FilterNull())
                // 개수가 없는 단어는 FilterNull()를 통해 필터링됨
                // Sum aggregator를 사용해 개수를 합하여 결과를 얻음(이건 어디 나와있는지 모르겠슴..)
               .project(new Fields("word", "count"));
        // 그런 다음 Trident는 자동으로 결과를 대기 중인 클라이언트로 다시 보냄
        return topology.build();
    }

    public static void main(String[] args) throws Exception{
        Config conf = new Config();
        conf.setMaxSpoutPending(20);
        String topoName = "210805_1";
        if(args.length > 0){
            topoName = args[0];
        }

        conf.setNumWorkers(3);
        StormSubmitter.submitTopologyWithProgressBar(topoName, conf, bulidTopology());
        try(DRPCClient drpc =DRPCClient.getConfiguredClient(conf)){
            for(int i = 0; i<10; i++){
                System.out.println("DRPC RESULT "+i+": " + drpc.execute("words", "cat the dog jumped"));
                Thread.sleep(1000);
            }
        }

    }
}
