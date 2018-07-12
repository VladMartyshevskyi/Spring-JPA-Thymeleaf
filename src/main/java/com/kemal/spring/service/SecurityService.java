package com.kemal.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.kemal.spring.domain.Card;
import com.kemal.spring.domain.CardRepository;
import com.kemal.spring.domain.User;
import com.kemal.spring.domain.UserRepository;

@Component("SecurityService")
public class SecurityService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CardRepository cardRepository;

	public Boolean isCardOwner(Authentication authentication, Long id) {

		User user = userRepository.findByUsername(authentication.getName());
		Card card = cardRepository.findById(id);

		if (card.getUser().equals(user)) {
			return true;
		}
		return false;
	}

	public Boolean isCardOwner(Authentication authentication, Card card) {

		User user = userRepository.findByUsername(authentication.getName());
		Card cardWithUser = cardRepository.findById(card.getId());
		if (cardWithUser.getUser().equals(user)) {
			return true;
		}
		return false;
	}
	
}