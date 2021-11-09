package TestPackage;

import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.tuple.TridentTuple;

public class MyFilter extends BaseFilter {
    @Override
    public boolean isKeep(TridentTuple tuple) {
        return tuple.getInteger(0)==1 && tuple.getInteger(1)==2;
    }
}

