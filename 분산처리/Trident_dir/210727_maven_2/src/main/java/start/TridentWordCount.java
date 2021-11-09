package start;

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

    public static StormTopology BuildTopology(){
        FixedBatchSpout spout = new FixedBatchSpout(new Fields("sentence"), 3,
                new Values("the cow jumped over the moon"),
                new Values("the man went to the store and bought some candy"),
                new Values("four score and seven years ago"),
                new Values("how many apples can you eat"),
                new Values("to be or not to be the person"));
        spout.setCycle(true);
        // 문장 스트림을 생성하기 위해 이 문장들을 순환해서 실행시킴

        TridentTopology topology = new TridentTopology();
        // newStream: 입력에서 새로운 스트림을 만듬, 여기서 입력: FixedBatchSpout에서 만들어진 것
        // 트라이덴트는 작은 batch로 입력 처리
        // .each: "sentence"라는 하나의 필드를 포함한 스트림을 방출함
            // 튜플마다 "sentence" 필드 가지고 단어 나누면서 Split 함수를 적용함
        // Count aggregator를 사용해 꾸준히 집계해줌
        TridentState wordCounts = topology.newStream("spout1", spout)
                .parallelismHint(16)
                .each(new Fields("sentence"),new Split(), new Fields("word"))
                .groupBy(new Fields("word"))
                .persistentAggregate(new MemoryMapState.Factory(), new Count(), new Fields("count"))
                .parallelismHint(16);

        topology.newDRPCStream("words")
                .each(new Fields("args"), new Split(), new Fields("word"))
                .stateQuery(wordCounts, new Fields("word"), new MapGet(), new Fields("count"))
                .each(new Fields("count"), new FilterNull())
                .project(new Fields("word", "count"));
        return topology.build();

    }

    public static void main(String[] args) throws Exception{
        Config conf = new Config();
        conf.setMaxSpoutPending(20);
        String topoName = "TridentWC";
        if (args.length > 0)
            topoName = args[0];
        conf.setNumWorkers(2);
        StormSubmitter.submitTopologyWithProgressBar(topoName, conf, BuildTopology());

        try(DRPCClient drpc = DRPCClient.getConfiguredClient(conf)){
            for(int i = 0; i<10; i++){
                System.out.println("DRPC RESULT: " + drpc.execute("words", "cat the dog jumped"));
                Thread.sleep(1000);
            }
        }
    }

    public static class Split extends BaseFunction {

        @Override
        public void execute(TridentTuple tuple, TridentCollector collector) {
            String sentence = tuple.getString(0);
            for (String word: sentence.split(" ")){
                collector.emit(new Values(word));
            }
        }
    }
}
