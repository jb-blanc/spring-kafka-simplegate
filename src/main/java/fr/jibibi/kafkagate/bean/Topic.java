package fr.jibibi.kafkagate.bean;

public class Topic {

	private String name;
	private Server server;
	private Integer partitions;
	private Short replicationFactor;
	
	public Topic() {
		super();
		this.name = "kafkagate.topic.test";
		this.server = new Server();
		this.partitions = 1;
		this.replicationFactor = 1;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getServerHost() {
		return server.getHost();
	}
	
	public void setServerHost(String serverHost) {
		this.server.setHost(serverHost);
	}
	
	public Integer getServerPort() {
		return server.getPort();
	}
	
	public void setServerPort(Integer serverPort) {
		this.server.setPort(serverPort);
	}

	public Integer getPartitions() {
		return partitions;
	}

	public void setPartitions(Integer partitions) {
		this.partitions = partitions;
	}

	public Short getReplicationFactor() {
		return replicationFactor;
	}

	public void setReplicationFactor(Short replicationFactor) {
		this.replicationFactor = replicationFactor;
	}
	
	
}
