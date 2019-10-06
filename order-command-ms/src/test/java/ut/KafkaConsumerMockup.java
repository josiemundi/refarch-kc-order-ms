package ut;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import ibm.gse.orderms.infrastructure.kafka.KafkaInfrastructureConfig;

public class KafkaConsumerMockup<K,V> extends KafkaConsumer<K,V>  {
	 protected V value;
	 protected K key;
	 protected String topicName = "orderCommands";
	 protected int partitionNumber = 0;
	 protected int lastCommittedOffet = 0;
	 
	 public KafkaConsumerMockup(Properties properties) {
		super(properties);
	}
	 
	 @SuppressWarnings("unchecked")
	public void setValue(String v) {
		 this.value = (V)v;
	 }
	 
	@SuppressWarnings("unchecked")
	public void setKey(String k) {
		 this.key = (K)k;
	}

	@Override
	 public ConsumerRecords<K,V> poll(final Duration timeout) {
		List<ConsumerRecord<K,V>> l = new ArrayList<ConsumerRecord<K,V>>();
		ConsumerRecord<K,V> cs = new ConsumerRecord<K,V>(this.topicName, 
				this.partitionNumber, 
				this.lastCommittedOffet, 
				(K)this.key, (V)this.value);
		l.add(cs);
		TopicPartition tp = new TopicPartition(KafkaInfrastructureConfig.ORDER_COMMAND_TOPIC,0);
		Map<TopicPartition,List<ConsumerRecord<K,V>>> m = new HashMap<TopicPartition,List<ConsumerRecord<K,V>>>();
		m.put(tp, l);
		ConsumerRecords<K,V> records = new ConsumerRecords<K,V>(m);
		return records; 
	 }
     
}

