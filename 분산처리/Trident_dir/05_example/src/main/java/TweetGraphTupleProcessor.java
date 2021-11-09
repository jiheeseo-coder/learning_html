import com.tinkerpop.blueprints.Graph;
//import com.tinkerpop.blueprints.Vertex;
import org.apache.tinkerpop.gremlin.structure.Vertex;
//import sun.security.provider.certpath.Vertex;

import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;

import twitter4j.JSONObject;

public class TweetGraphTupleProcessor implements GraphTupleProcessor{
    @Override
    public void process(Graph G, TridentTuple tuple, TridentCollector collector) {
        Long timestamp = tuple.getLong(0);
        JSONObject json = (JSONObject) tuple.get(1);

        Vertex user =

    }
}
