package fr.jibibi.kafkagate.rest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.jibibi.kafkagate.bean.Server;
import fr.jibibi.kafkagate.bean.Topic;

@RestController
@RequestMapping("/admin")
public class AdministrationREST {
	private static final Logger logger = LoggerFactory.getLogger(AdministrationREST.class);

	@RequestMapping(method=RequestMethod.PUT, consumes="application/json",path="/topic")
	private Topic createKafkaTopic(@RequestBody Topic topic) throws InterruptedException, ExecutionException {
		logger.info("[KAFKAGATE] Topic '{}' creation asked on server {}:{}",topic.getName(), topic.getServerHost(), topic.getServerPort());
		AdminClient admin = AdminClient.create(getConfigMap(topic));
		CreateTopicsResult result = admin.createTopics(Arrays.asList(new NewTopic(topic.getName(), topic.getPartitions(), topic.getReplicationFactor())));
		result.all().get();
		admin.close();
		return topic;
	}
	
	@RequestMapping(method=RequestMethod.GET, consumes="application/json",path="/topic/list")
	private Set<String> listTopics(@RequestBody Server server) throws InterruptedException, ExecutionException {
		logger.info("[KAFKAGATE] Listing topics on server {}:{}", server.getHost(), server.getPort());
		AdminClient admin = AdminClient.create(getConfigMap(server));
		Set<String> result = admin.listTopics().names().get();
		admin.close();
		return result;
	}
	
	private Map<String, Object> getConfigMap(Topic topic){
	    return getConfig(topic.getServerHost(), topic.getServerPort());
	}
	
	private Map<String, Object> getConfigMap(Server server){
		return getConfig(server.getHost(), server.getPort());
	}
	
	private Map<String, Object> getConfig(String host, Integer port){
		Map<String, Object> configs = new HashMap<>();
	    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, String.format("http://%s:%d", host, port));
	    return configs;
	}
}
