package com.cts.kafka.producer.console.app;

import java.util.Properties;
import java.util.Scanner;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaConsoleProducer {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		
		//Config the producer properties
		Properties producerProps = new Properties();
		producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
		
		//Create the kafka producer;
		
		KafkaProducer<String,String> kafkaProducer = new KafkaProducer<String, String>(producerProps);
		
		boolean shallContinue=true;
		
		while(shallContinue) {
			String key = "TopicA";
			System.out.print("Message: ");
			String value = scan.nextLine();
			
			ProducerRecord<String,String> msg = new ProducerRecord<String, String>(key, value);
			kafkaProducer.send(msg);
			kafkaProducer.flush();
			
			System.out.print("Continue(yes/no): ");
			shallContinue = "yes".equals(scan.nextLine());
		}
		
		kafkaProducer.close();
		scan.close();
	}

}
