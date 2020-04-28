package com.kafka.producer.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.kafka.producer.model.User;

@Configuration
public class KafkaConfiguration {

	private final static String BOOTSTRAP_SERVERS = "localhost:9092";

	@Bean
	public ProducerFactory<String, String> simpleProducerFactory() 
	{

		Map<String, Object> props = new HashMap<>();

		// props.put(ProducerConfig.CLIENT_ID_CONFIG,"clickStreamProducer");
		// props.put(ProducerConfig.ACKS_CONFIG, "all");
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		return new DefaultKafkaProducerFactory<>(props);

	}

	@Bean(name = "simpleProducer")
	public KafkaTemplate<String, String> simpleKafkaTemplate() 
	{
		return new KafkaTemplate<>(simpleProducerFactory());
	}

	@Bean
	public ProducerFactory<String, User> userProducerFactory() 
	{
		
		Map<String, Object> config = new HashMap<>();

		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return new DefaultKafkaProducerFactory<>(config);
		
	}

	@Bean(name = "userProducer")
	public KafkaTemplate<String, User> userKafkaTemplate() 
	{
		return new KafkaTemplate<>(userProducerFactory());
	}

}
