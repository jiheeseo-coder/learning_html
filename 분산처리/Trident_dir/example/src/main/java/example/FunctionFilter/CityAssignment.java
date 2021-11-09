package example.FunctionFilter;

import example.Spout.DiagnosisEvent;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityAssignment extends BaseFunction {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(CityAssignment.class);



    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
        Map<String, double[]> CITIES =new HashMap<String, double[]>();

        { // 예제에서 사용하는 도시만 초기화
            double[] phl = {39.875365, -75.249524};
            CITIES.put("PHL", phl);
            double[] nyc = {40.71448, -74.00598};
            CITIES.put("NYC", nyc);
            double[] sf = {-31.4250142, -62.0841809};
            CITIES.put("SF", sf);
            double[] la = {-34.05374, -118.24307};
            CITIES.put("LA", la);
        }
        DiagnosisEvent diagnosis = (DiagnosisEvent) tuple.getValue(0);
        double leastDistance = Double.MAX_VALUE;
        String closesCity = "NONE";

        // 가장 근접한 도시 찾기
        for (Map.Entry<String, double[]> city: CITIES.entrySet()){
            double R = 6371;
            double x = (city.getValue()[0] - diagnosis.lng) *
                    Math.cos((city.getValue()[0] + diagnosis.lng) / 2);
            double y = (city.getValue()[1] - diagnosis.lat);
            double d = Math.sqrt(x * x + y * y) * R;
            if (d<leastDistance){
                leastDistance = d;
                closesCity = city.getKey();
            }
        }

        // 값 내보내기
        List<Object> values = new ArrayList<Object>();
        values.add(closesCity);
        LOG.debug("Closet city to lat=[" + diagnosis.lat + "], lng=[" + diagnosis.lng +"] == [" +
                closesCity + "], d=[" + leastDistance + "]");
        collector.emit(values);

    }
}
