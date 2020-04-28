package com.kafka.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.kafka.producer.service.ProducerService;

@SpringBootApplication
public class ProducerApplication 
{

	public static void main(String[] args) 
	{
		ApplicationContext context = SpringApplication.run(ProducerApplication.class, args);
		ProducerService service = context.getBean(ProducerService.class);
		service.execute();
		service.executeJSON();
	}

}
