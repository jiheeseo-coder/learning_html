package TestPackage;

import org.apache.storm.trident.testing.FixedBatchSpout;

public class example {
    FixedBatchSpout spout = new FixedBatchSpout(allFields, 10, Vehicle.generateVehicles(20));
}
