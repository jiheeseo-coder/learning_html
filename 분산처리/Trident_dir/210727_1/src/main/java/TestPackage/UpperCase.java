package TestPackage;

import org.apache.storm.trident.operation.MapFunction;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;

public class UpperCase implements MapFunction {
    @Override
    public Values execute(TridentTuple input) {
        return new Values(input.getString(0).toUpperCase());
    }
}

