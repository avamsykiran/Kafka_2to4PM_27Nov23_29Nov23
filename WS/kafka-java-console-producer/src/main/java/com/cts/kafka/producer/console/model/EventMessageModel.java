package com.cts.kafka.producer.console.model;

import java.io.Serializable;
import java.util.Objects;

public class EventMessageModel implements Serializable {
	
	private long eventId;
	private String event;
	
	public EventMessageModel() {
		// TODO Auto-generated constructor stub
	}

	public EventMessageModel(long eventId, String event) {
		super();
		this.eventId = eventId;
		this.event = event;
	}

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	@Override
	public int hashCode() {
		return Objects.hash(event, eventId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventMessageModel other = (EventMessageModel) obj;
		return Objects.equals(event, other.event) && eventId == other.eventId;
	}

	@Override
	public String toString() {
		return "EventMessageModel [eventId=" + eventId + ", event=" + event + "]";
	}
	
	
}
