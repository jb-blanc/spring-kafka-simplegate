package fr.jibibi.kafkagate.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class Message {

	private Topic topic;

	@JsonProperty(required=true)
	private JsonNode json;

	public Message() {
		super();
		this.topic = new Topic();
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public JsonNode getJson() {
		return json;
	}

	public void setJson(JsonNode json) {
		this.json = json;
	} 
	
	
}
