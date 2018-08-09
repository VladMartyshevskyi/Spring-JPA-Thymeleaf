package com.kemal.spring.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
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
	private KafkaTemplate<String, String> kafkaTemplate;
	
	private Gson gson = new Gson();
	
	public void save(Review review) {
		kafkaTemplate.send(topic, messageKey, gson.toJson(review));
	}

}
