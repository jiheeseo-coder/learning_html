import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.GraphFactory;
import org.apache.commons.configuration.BaseConfiguration;


import java.util.Map;

public class TitanGraphFactory extends GraphFactory {
    public static final String STORAGE_BACKEND = "titan.storage.backend";
    public static final String STORAGE_HOSTNAME = "titan.storage.hostname";

    public TitanGraph makeGraph (Map conf){
        BaseConfiguration graphConf = new BaseConfiguration();
        graphConf.setProperty("storage.backend", conf.get(STORAGE_BACKEND));
        graphConf.setProperty("storage.hostname", conf.get(STORAGE_HOSTNAME));

        return TitanFactory.open(graphConf);
    }

}
