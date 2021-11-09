package TestPackage;

import org.apache.storm.trident.operation.BaseAggregator;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;

public class CountAgg extends BaseAggregator<CountAgg.CountState> {
    @Override
    public CountState init(Object batchId, TridentCollector collector) {
        return new CountState();
    }

    @Override
    public void aggregate(CountState val, TridentTuple tuple, TridentCollector collector) {
        val.count += 1;
    }

    @Override
    public void complete(CountState val, TridentCollector collector) {
        collector.emit(new Values(val.count));
    }

    static class CountState{
        long count = 0;
    }
}
