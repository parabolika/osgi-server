package com.parabolika.server.common;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.event.Event;

public class EventBuilder {
	private final String topic;
	private final Dictionary<String, Object> options;

	public EventBuilder(String topic) {
		this(topic, new Hashtable<String, Object>());
	}

	public EventBuilder(String topic, Dictionary<String, Object> options) {
		this.topic = topic;
		this.options = options;
	}

	public EventBuilder withOption(String key, Object value) {
		options.put(key, value);
		return this;
	}

	public EventBuilder withOptions(Dictionary<String, Object> options) {
		return this;
	}

	public Event build() {
		return new Event(topic, options);
	}

	public Dictionary<String, Object> getOptions() {
		return options;
	}
}
