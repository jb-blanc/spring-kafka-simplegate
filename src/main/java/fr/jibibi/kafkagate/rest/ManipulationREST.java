package fr.jibibi.kafkagate.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import fr.jibibi.kafkagate.bean.Message;
import fr.jibibi.kafkagate.bean.Topic;

@RestController
@RequestMapping("/topic")
public class ManipulationREST {
	private static final Logger logger = LoggerFactory.getLogger(ManipulationREST.class);

	@RequestMapping(method=RequestMethod.PUT, consumes="application/json",path="/send")
	private JsonNode createKafkaTopic(@RequestBody Message message) throws InterruptedException, ExecutionException {
		logger.info("[KAFKAGATE] Sending '{}' to topic '{}' on server {}:{}",message.getJson(), message.getTopic().getName(), message.getTopic().getServerHost(), message.getTopic().getServerPort());
		
		KafkaTemplate<Integer, String> template = new KafkaTemplate<>(getProducerFactory(message.getTopic()), true);
		
		template.send(message.getTopic().getName(), message.getJson().toString()).get();
		template.flush();
		
		return message.getJson();
	}
	
	
	private DefaultKafkaProducerFactory<Integer, String> getProducerFactory(Topic topic){
		return new DefaultKafkaProducerFactory<>(getConfigMap(topic));
	}
	
	private Map<String, Object> getConfigMap(Topic topic){
		Map<String, Object> configs = new HashMap<>();
	    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, String.format("http://%s:%d", topic.getServerHost(), topic.getServerPort()));
	    configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	    configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	    return configs;
	}
}
