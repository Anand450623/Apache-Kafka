package com.kafka.processor.resource;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Processor 
{
	
	@Value("${kafka.topic.output}")
    private String outTopic;
	
	public void process(KStream<String,String> stream)
	{
		stream.filter((k,v)->v.length()%2==0)
			.mapValues(v-> v+"	forwarded")
			.to(outTopic);
	}
	
}
