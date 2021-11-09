import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.BaseAggregator;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.trident.windowing.InMemoryWindowsStoreFactory;
import org.apache.storm.trident.windowing.config.*;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.concurrent.TimeUnit;

public class WE_TP {
    public static StormTopology buildTopology() throws Exception{
        TridentTopology topology = new TridentTopology();
        FixedBatchSpout spout = new FixedBatchSpout(new Fields("char"), 3, new Values("A"),
                new Values("B"), new Values("C"), new Values("D"), new Values("E"),
                new Values("F"), new Values("G"), new Values("H"), new Values("I"),
                new Values("J"), new Values("K"), new Values("L"), new Values("M"),
                new Values("N"), new Values("O"), new Values("P"), new Values("Q"),
                new Values("R"), new Values("S"));

        spout.setCycle(true);

        WindowConfig windowConfig;
        windowConfig = TumblingDurationWindow.of(new BaseWindowedBolt.Duration(1000, TimeUnit.MILLISECONDS));

        topology.newStream("char", spout)
                .window(windowConfig, new InMemoryWindowsStoreFactory(), new Fields("char"), new BaseAggregator() {

                    private StringBuilder sb;

                    @Override
                    public Object init(Object batchId, TridentCollector collector) {
                        this.sb = new StringBuilder();
                        return null;
                    }

                    @Override
                    public void aggregate(Object val, TridentTuple tuple, TridentCollector collector) {
                        sb.append(tuple.getString(0));
                    }

                    @Override
                    public void complete(Object val, TridentCollector collector) {
                        collector.emit(new Values(this.sb.toString()));
                    }
                }, new Fields("merge"))
                .peek(tuple -> System.out.println(tuple));
        return topology.build();
    }

    public static void main(String[] args) throws Exception {
        Config conf = new Config();
        conf.setDebug(false);
        conf.setNumWorkers(1);

        StormSubmitter.submitTopologyWithProgressBar("tumbling", conf, buildTopology());
        //Thread.sleep(20000);

    }
}
