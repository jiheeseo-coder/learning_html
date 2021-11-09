package Exam;

import clojure.reflect.Field;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class test1 {
    FixedBatchSpout spout = new FixedBatchSpout(new Fields("sentence"),3,
            new Values("the cow jumped over the moom"),
            new Values("the man went to the store and bought some candy"),
            new Values("four score and seven years ago"),
            new Values("how many apples can you eat"));
    spout.setCycle(true);

    TridentTopology topology = new TridentTopology();
    TridentState wordCounts=
            topology.newStream("spout1", spout)
}
