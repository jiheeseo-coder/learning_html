package TestPackage;

import org.apache.storm.trident.operation.ReducerAggregator;
import org.apache.storm.trident.tuple.TridentTuple;

public class Count2 implements ReducerAggregator<Long> {
    @Override
    public Long init() {
        return 0L;
    }

    @Override
    public Long reduce(Long curr, TridentTuple tuple) {
        return curr+1;
    }
}
