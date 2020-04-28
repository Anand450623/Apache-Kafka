package code;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;

public class Processor 
{
	
	public static void main(final String[] args) throws Exception 
	{

		Properties clickStreamConfig = new Properties();
		clickStreamConfig.put(StreamsConfig.APPLICATION_ID_CONFIG, "test");
		clickStreamConfig.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		clickStreamConfig.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		clickStreamConfig.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

		// Create a stream builder
		StreamsBuilder clickStreamBuilder = new StreamsBuilder();

		// Stream the messages from the specified Topic
		KStream<String, String> StreamData = clickStreamBuilder.stream("test");

		// Perform filter operation on SessionTime parameter
		KStream<String, String> FilteredRecords = StreamData.filter(

				new Predicate<String, String>() {

					// Filter the records where SessionTime is less than 30 seconds
					public boolean test(String Key, String Value) {

						return Value.length() % 2 == 0;

					}
				}

		);

		// Write filtered records to a new topic
		FilteredRecords.to("test1");

		System.out.println("Cleansing the records is complete");

		// Create KafkaStreams object and start the process
		KafkaStreams streams = new KafkaStreams(clickStreamBuilder.build(), clickStreamConfig);

		streams.start();
	}
	
}
