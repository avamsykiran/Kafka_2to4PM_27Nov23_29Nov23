package com.cts.kafka.consumer.console.app;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaConsoleConsumer {
	public static void main(String[] args) {

		//Config the consumer properties
		Properties consumerProps = new Properties();
		consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
		/*
		 * earliest
		 * latest
		 * any other
		 * none
		 * */
		consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
		consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG,"myJavaApp");
		
		//Create the kafka consumer;
		
		KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String, String>(consumerProps);
		
		kafkaConsumer.subscribe(Arrays.asList("TopicA"));
		
		while(true) {
			ConsumerRecords<String, String> msgs = kafkaConsumer.poll(Duration.ofMillis(100));
			for(ConsumerRecord<String, String> msg : msgs) {
				System.out.println(msg.topic() + "\t" + msg.key() + "\t" + msg.value());
			}
		}
		
	}
}
