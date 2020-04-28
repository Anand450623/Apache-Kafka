package com.kafka.producer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.kafka.producer.model.User;
import com.kafka.producer.service.ProducerService;

@Service
public class ProducerServiceImpl implements ProducerService 
{

	@Autowired
	@Qualifier("simpleProducer")
	KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	@Qualifier("userProducer")
	KafkaTemplate<String, User> kafkaTemplate1;
	
	@Override
	public void execute() 
	{
		kafkaTemplate.send("test","key","value");
		System.err.println("message send");
	}

	// just to show that key  is optional...
	@Override
	public void executeJSON() 
	{
		kafkaTemplate1.send("test",new User("fname", "Technology"));
	}

}
