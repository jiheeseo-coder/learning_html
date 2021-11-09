import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class Topo {
    public static void main(String[] args) throws AuthorizationException, InvalidTopologyException, AlreadyAliveException {
        TopologyBuilder builder = new TopologyBuilder();

        // setBolt: Define a new bolt in this topology with parallelism of just one thread
        builder.setSpout("kafka-spout", new Spout());
        builder.setBolt("split-bolt", new SplitSentenceBolt()).shuffleGrouping("kafka-spout");
        builder.setBolt("count-bolt", new WordCountBolt()).fieldsGrouping("split-bolt", new Fields("word"));
        builder.setBolt("report-bolt", new ReportBolt()).globalGrouping("count-bolt");
        // id: the id of this component. This id is referenced by other components
        //     that want to consume this bolt's outputs.

        Config config = new Config();
        StormSubmitter.submitTopologyWithProgressBar("topo",config, builder.createTopology());
    }
}
