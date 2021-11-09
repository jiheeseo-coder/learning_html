import org.apache.storm.trident.operation.CombinerAggregator;
import org.apache.storm.trident.tuple.TridentTuple;

public class One implements CombinerAggregator<Integer> {
    public Integer init(TridentTuple tuple) {
        return 1;
    }

    public Integer combine(Integer val1, Integer val2) {
        return 1;
    }

    public Integer zero() {
        return 1;
    }
}
