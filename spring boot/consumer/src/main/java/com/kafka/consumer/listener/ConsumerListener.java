package com.kafka.consumer.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.kafka.consumer.model.User;

@Component
public class ConsumerListener 
{
	
	@KafkaListener(topics = "test", groupId = "group_id")
	public void consume(Message<String> message) 
	{
		System.out.println("Consumed message: " + message);
	}

	@KafkaListener(topics = "test1", groupId = "group_json", containerFactory = "userKafkaListenerFactory")
	public void consumeJson(User user) 
	{
		System.out.println("Consumed JSON Message: " + user);	
	}
	
}
