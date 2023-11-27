package com.cts.kafka.consumer.console.util;

import org.apache.kafka.common.serialization.Deserializer;

import com.cts.kafka.consumer.console.model.EventMessageModel;

public class EventMessageDeserializer implements Deserializer<EventMessageModel> {

	@Override
	public EventMessageModel deserialize(String topic, byte[] data) {
		EventMessageModel model = new EventMessageModel();
		
		String json = new String(data);
		
		String fields[] = json.split(",");
		
		model.setEventId(Long.parseLong(fields[0].substring("{\"eventId\":".length())));
		model.setEvent(fields[1].substring("\"event\":".length(),fields[1].lastIndexOf("}")));
		return model;
	}

}
