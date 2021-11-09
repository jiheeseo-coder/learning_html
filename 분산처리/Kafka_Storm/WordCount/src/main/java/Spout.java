import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class Spout extends BaseRichSpout {
    public KafkaConsumer<String, String> consumer;
    private SpoutOutputCollector collector;

    @Override
    public void open(Map<String, Object> conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"114.70.235.40:19092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group");
        // 이건 왜 하는 건지 모르겠음..
        // 암튼 그냥 이건 설정을 추가해주는 함수네, 저 Deserializer는 UTF-8 정보를 갖고 있음

        this.consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("inputTopic"));

    }

    @Override
    public void nextTuple() {
        String message = null;
        try{
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10000));
            for(ConsumerRecord<String, String> record: records){
                message = record.value();
                this.collector.emit(new Values(message));
            }
        }
        catch(Exception e){

        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("sentence"));
    }
}
