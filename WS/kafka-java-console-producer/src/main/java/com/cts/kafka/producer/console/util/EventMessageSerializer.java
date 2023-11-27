package com.cts.kafka.producer.console.util;

import org.apache.kafka.common.serialization.Serializer;

import com.cts.kafka.producer.console.model.EventMessageModel;

public class EventMessageSerializer implements Serializer<EventMessageModel>{

	@Override
	public byte[] serialize(String topic, EventMessageModel data) {
		
		StringBuffer buff = new StringBuffer();
		buff.append("{");
		buff.append("\"eventId\":"+data.getEventId()+",");
		buff.append("\"event\":"+data.getEvent());
		buff.append("}");
		
		String json= new String(buff);
		return json.getBytes();
	}

}
