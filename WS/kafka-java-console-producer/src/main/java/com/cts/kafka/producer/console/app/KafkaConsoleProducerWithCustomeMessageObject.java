package com.cts.kafka.producer.console.app;

import java.util.Properties;
import java.util.Scanner;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import com.cts.kafka.producer.console.model.EventMessageModel;
import com.cts.kafka.producer.console.util.EventMessageSerializer;

public class KafkaConsoleProducerWithCustomeMessageObject {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		
		//Config the producer properties
		Properties producerProps = new Properties();
		producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, EventMessageSerializer.class.getName());
		producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
		
		//Create the kafka producer;
		
		KafkaProducer<String,EventMessageModel> kafkaProducer = new KafkaProducer<>(producerProps);
		
		boolean shallContinue=true;
		
		String topic = "TopicEvent";
		
		while(shallContinue) {	
			System.out.print("Key: ");
			String key = scan.next();
			EventMessageModel model = new EventMessageModel();
			System.out.print("EventId: ");
			model.setEventId(scan.nextLong());
			System.out.print("Event: ");
			model.setEvent(scan.next());
			
			ProducerRecord<String,EventMessageModel> msg = new ProducerRecord<>(topic,key,model);
			kafkaProducer.send(msg,(resultantRecord,exception)->{
				if(exception!=null) {
					System.out.println("Unble to send the message:: "+exception.getMessage());
				}else {
					System.out.println("Message is send to " 
				+ resultantRecord.partition() + " by " + resultantRecord.timestamp());
				}
			});
			kafkaProducer.flush();
			
			System.out.print("Continue(true/false): ");
			shallContinue =scan.nextBoolean();
		}
		
		kafkaProducer.close();
		scan.close();
	}

}
