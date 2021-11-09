import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RogueApplication{
    private static final Logger LOG = LoggerFactory.getLogger(RogueApplication.class);

    public static void main(String[] args) throws Exception{
        int slowCount = 6;
        int fastCount = 15;

        // 느린 간격
        for (int i = 0; i<slowCount; i++){
            LOG.warn("This is a warning (slow state).");
            Thread.sleep(1000);
        }

        // 빠른 간격
        for (int i = 0; i<fastCount; i++){
            LOG.warn("This is a warning (rapid state).");
            Thread.sleep(1000);
        }

        // 느린 간격으로 복귀
        for(int i = 0; i<slowCount; i++){
            LOG.warn("This is a warning (slow state).");
            Thread.sleep(5000);
        }
    }
}
