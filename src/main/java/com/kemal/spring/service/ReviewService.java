package com.kemal.spring.service;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.kemal.spring.domain.Review;

@Service
public class ReviewService {
	
	@Value("${kafka.message.key}")
	private String messageKey;
	
	@Value("${kafka.topic}")
	private String topic;
	@Autowired
	private Producer<String, String> producer;
	
	private Gson gson = new Gson();
	
	public void save(Review review) {
		producer.send(new ProducerRecord<String, String>(topic, messageKey, gson.toJson(review)));
	}

}
