import com.tinkerpop.blueprints.Graph;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;

public interface GraphTupleProcessor {
    public void process(Graph G, TridentTuple tuple, TridentCollector collector);
}
