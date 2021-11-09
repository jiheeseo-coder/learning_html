package example;

import example.CombinerReducer.Count;
import example.FunctionFilter.*;
import example.Spout.DiagnosisEventSpout;
import example.State.OutbreakTrendFactory;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.tuple.Fields;

public class Topology {
    public static StormTopology buildTopology() {
        TridentTopology topology = new TridentTopology();
        DiagnosisEventSpout spout = new DiagnosisEventSpout();
        Stream inputStream = topology.newStream("event", spout);

        inputStream.each(new Fields("event"), new DiseaseFilter())
                   .each(new Fields("event"), new CityAssignment(), new Fields("city"))
                   .each(new Fields("event", "city"), new HourAssignment(), new Fields("hour", "cityDiseaseHour"))
                   .groupBy(new Fields("cityDiseaseHour"))
                   .persistentAggregate(new OutbreakTrendFactory(), new Count(), new Fields("count"))
                   .newValuesStream()
                   .each(new Fields("cityDiseaseHour", "count"), new OutbreakDetector(), new Fields("alert"))
                   .each(new Fields("alert"), new DispatchAlert(), new Fields());
        return topology.build();
    }

    public static void main(String[] args) throws Exception {
        Config conf = new Config();
        conf.setDebug(true);
        conf.setNumWorkers(5);

        StormSubmitter.submitTopologyWithProgressBar("jhseo", conf, buildTopology());
        Thread.sleep(200000);

    }
}
