package com.kemal.spring.configuration;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

	@Bean
	public Producer<String, String> getProperties(@Value("${kafka.bootstrap.servers}") String bootstrapServers,
			@Value("${kafka.acks}") String acks, @Value("${kafka.linger.ms}") String lingerMs) {
		Properties properties = new Properties();
		properties.put("bootstrap.servers", bootstrapServers);
		properties.put("acks", acks);
		properties.put("linger.ms", lingerMs);
		properties.put("key.serializer", StringSerializer.class.getName());
		properties.put("value.serializer", StringSerializer.class.getName());

		return new KafkaProducer<String, String>(properties);

	}

}
