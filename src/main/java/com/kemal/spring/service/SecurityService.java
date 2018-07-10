package com.kemal.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.kemal.spring.domain.Card;

@Component("SecurityService")
public class SecurityService {

	@Autowired
	private UserService userService;
	
	public Boolean isCardOwner(Authentication authentication, Long id) {
		
		List<Card> cards = userService.findByUsername(authentication.getName()).getCards();
		for(Card ownedCard : cards) {
			if(ownedCard.getId().equals(id)) {
				return true;
			}	
		}
		return false;
	}
}
