import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.streams.Pair;
import org.apache.storm.streams.StreamBuilder;
import org.apache.storm.streams.operations.CombinerAggregator;
import org.apache.storm.streams.operations.mappers.ValueMapper;
import org.apache.storm.streams.windowing.TumblingWindows;
import org.apache.storm.topology.base.BaseWindowedBolt;

/*
* global aggregate 예시
* */
public class AggregateExample {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        StreamBuilder bulider = new StreamBuilder();
        /*
        * 스파우트에 의해 방출된 숫자 스트림의 평균을 계산함.
        * 각 파티션의 sum과 count는 축적되고 downstream task로 방출됨
        * downstream task: 부분적으로 축적된 결과를 합치고 마지막 결과는 방출됨
        * */
        bulider.newStream(new RandomIntegerSpout(), new ValueMapper<Integer>(0),2)
                .window(TumblingWindows.of(BaseWindowedBolt.Duration.seconds(5)))
                // Duration: Holds a Time duration for time based windows and sliding intervals.
                .filter(x -> x>0 && x<500)
                // x값이 (0, 500) 이렇게 되네, 근데 RandomIntegerSpout()는 1000까지 숫자에서 랜덤하게 하는데
                .aggregate(new Avg())
                // 여기의 내부적으로 init(), apply() ... CombinerAggregator의 함수들을 호출하겠지,,?
                // 일단 이렇게만 봐서는 어디서 init 호출하고,, apply 호출하고,, 이런걸 모르겠음
                .print();

        Config config = new Config();
        String topoName = "AGG_EXAMPLE";
        if(args.length > 0)
            topoName = args[0];
        config.setNumWorkers(1);
        StormSubmitter.submitTopologyWithProgressBar(topoName, config, bulider.build());


    }

    private static class Avg implements CombinerAggregator<Integer, Pair<Integer, Integer>, Double>{
        /*
        * 여기서 구현된 CombinerAggregator 탐방하기 재미있슴,, 왜냐하면 예전에 공부한 CombinerAggregator가
        * 아니기 때문! */

        @Override
        public Pair<Integer, Integer> init() {
            return Pair.of(0,0);
        }

        @Override
        public Pair<Integer, Integer> apply(Pair<Integer, Integer> accumulator, Integer value) {
            return Pair.of(accumulator.value1 + value, accumulator.value2+1);
            // 이게 뭐람 pair를 받아서 value1쪽에는 value를 더하고 value2쪽에는 1을 더해서 만들어진 새로운 pair를
            // 리턴하네,,
        }

        @Override
        public Pair<Integer, Integer> merge(Pair<Integer, Integer> accum1, Pair<Integer, Integer> accum2) {
            System.out.println("Merge" + accum1 + " and "+accum2);
            return Pair.of(accum1.value1 + accum2.value1, accum1.value2 + accum1.value2);
            /*
            * 두 개의 pair를 하나로 합치는거네,, accum1과 accum2를 더하는데 value1끼리 value2끼리 */
        }

        @Override
        public Double result(Pair<Integer, Integer> accum) {
            return (double) accum.value1 / accum.value2;
        }
        /*
        * result 결과가 이렇게 나오는거 보니까 value1은 sum이고 value2는 count네*/
    }
}
