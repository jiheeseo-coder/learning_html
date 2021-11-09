package TestPackage;


import org.apache.storm.trident.operation.FlatMapFunction;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;

import java.util.ArrayList;
import java.util.List;

public class Split implements FlatMapFunction {

    @Override
    public Iterable<Values> execute(TridentTuple input) {
        List<Values> valuesList = new ArrayList<>();
        for (String word: input.getString(0).split(" ")){
            valuesList.add(new Values(word));
        }
        return valuesList;
    }
}

