package fr.jibibi.kafkagate.bean;

public class Server {

	private String host;
	private Integer port;
	
	public Server() {
		super();
		this.host = "localhost";
		this.port = 9092;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	
	
	
}
