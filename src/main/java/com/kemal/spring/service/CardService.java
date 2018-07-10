package com.kemal.spring.service;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kemal.spring.domain.Card;
import com.kemal.spring.domain.CardRepository;
import com.kemal.spring.domain.User;
@Service
public class CardService {
	
	@Autowired
	private CardRepository cardRepository;

	public void save(Card card) {
		cardRepository.save(card);
	}

	@Transactional
	@PreAuthorize("@SecurityService.isCardOwner(authentication, #id) or hasRole('ROLE_ADMIN')")
	public void deleteById(Long id) {
		cardRepository.deleteById(id);
		
	}
	@Transactional
	@PreAuthorize("@SecurityService.isCardOwner(authentication, #id) or hasRole('ROLE_ADMIN')")
	public Card findById(Long id) {
		return cardRepository.findById(id);
	}

	public void update(Card card) {
		Card foundCard = cardRepository.findById(card.getId());
		if(foundCard != null) {
			foundCard.setBank(card.getBank());
			foundCard.setCvv(card.getCvv());
			foundCard.setExpiresMonth(card.getExpiresMonth());
			foundCard.setExpiresYear(card.getExpiresYear());
			foundCard.setNumber(card.getNumber());
		}
		save(foundCard);
	}

}
