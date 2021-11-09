package wordcount;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;

/*
고정된 수의 문장들을 반복해서 내보내는 데이터 소스를 시뮬레이션 하도록 구현. 각 문장은 단일 필드를가진 튜플로 보내짐
 */

public class SentenceSpout extends BaseRichSpout {
    // BaseRichSpout:
    private SpoutOutputCollector collector;
    private int index = 0;
    private final String[] sentences ={
            "Concentration comes out of a combination of confidence and hunger",
            "First keep the peace within yourself, then you can also bring peace to others",
            "Breathe. Let go. And remind yourself that this very moment is the only one you know you have for sure",
            "Many will call me an adventurer - and that I am, only one of a different sort: one of those who risks his skin to prove his platitudes",
            "It's not that I'm so smart , it's just that I stay with problems longer"
    };

    @Override
    public void open(Map<String, Object> map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
    }

    @Override
    public void nextTuple() {
        this.collector.emit(new Values(sentences[index]));
        index++;
        if(index >= sentences.length){
            index = 0;
        }
        try {Thread.sleep(5);}catch (InterruptedException e){}
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("sentence"));
    }
}
