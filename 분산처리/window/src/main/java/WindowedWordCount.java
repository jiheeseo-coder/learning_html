import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.streams.Pair;
import org.apache.storm.streams.StreamBuilder;
import org.apache.storm.streams.operations.mappers.ValueMapper;
import org.apache.storm.streams.windowing.TumblingWindows;
import org.apache.storm.topology.base.BaseWindowedBolt;

import java.util.Arrays;

public class WindowedWordCount {
    public static void main(String[] args) throws Exception {
        StreamBuilder bulider = new StreamBuilder();
        bulider.newStream(new RandomSentenceSpout(), new ValueMapper<String>(0),2)
                .window(TumblingWindows.of(BaseWindowedBolt.Duration.seconds(2)))
                .flatMap(s -> Arrays.asList(s.split(" ")))
                .mapToPair(w -> Pair.of(w, 1))
                .countByKey()
                .filter(x -> x.getSecond() >= 5)
                .print();

        Config config = new Config();
        String topoName = "test";
        if (args.length > 0){
            topoName = args[0];
        }
        config.setNumWorkers(1);
        StormSubmitter.submitTopologyWithProgressBar(topoName, config, bulider.build());
    }
}
