package com.kafka.processor.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import com.kafka.processor.resource.Processor;

@EnableKafka
@Configuration
@EnableKafkaStreams
@PropertySource("classpath:application.yml")
public class ProcessorConfiguration 
{
	
	@Value("${kafka.topic.input}")
    private String inputTopic;
	
	@Autowired
	Processor processor; 
	
	@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
	public KafkaStreamsConfiguration kstreamConfigs(KafkaProperties kafkaProperties)
	{
		Map<String,Object> config = new HashMap<>();
		config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
		config.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaProperties.getClientId());
		config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		return new KafkaStreamsConfiguration(config);
	}
	
	@Bean
	public KStream<String, String> kStream(StreamsBuilder kStreamBuilder)
	{
		KStream<String, String> stream = kStreamBuilder.stream(inputTopic);
		processor.process(stream);
		return stream;
	}
	
}
