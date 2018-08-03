package com.kemal.spring.service;

import java.util.List;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.kemal.spring.domain.Card;
import com.kemal.spring.domain.CardRepository;
import com.kemal.spring.web.controllers.viewControllers.CardController;


@Service
public class CardService {

	private static Logger logger = LoggerFactory.getLogger(CardService.class);
	@Autowired
	private CardRepository cardRepository;

	public void save(Card card) {
		cardRepository.save(card);
		logger.info("Card {} was saved", card);
	}

	@Transactional
	@PreAuthorize("@SecurityService.isCardOwner(authentication, #id) or hasRole('ROLE_ADMIN')")
	public void deleteById(Long id) {
		cardRepository.deleteById(id);
		logger.info("Card with id {} was deleted", id);
	}

	@Transactional
	@PreAuthorize("@SecurityService.isCardOwner(authentication, #id) or hasRole('ROLE_ADMIN')")
	public Card findById(Long id) {
		logger.debug("Method findById with id {} was invoked", id);
		return cardRepository.findById(id);
	}

	@Transactional
	@PreAuthorize("@SecurityService.isCardOwner(authentication, #card) or hasRole('ROLE_ADMIN')")
	public void update(Card card) {
		logger.debug("Method update was invoked with card: {}", card);
		Card foundCard = cardRepository.findById(card.getId());
		if (foundCard != null) {
			foundCard.setBank(card.getBank());
			foundCard.setCvv(card.getCvv());
			foundCard.setExpiresMonth(card.getExpiresMonth());
			foundCard.setExpiresYear(card.getExpiresYear());
			foundCard.setNumber(card.getNumber());
			save(foundCard);
			logger.info("Card was updated : {} ", foundCard);
		} else {
			logger.error("Card with id {} is not found to be updated", card.getId());
			throw new RuntimeException("Card with id " + card.getId() + " is not found to be updated");
		}
	}

	public List<Card> findByUserId(Long id) {
		logger.debug("Method findByUserId with id {} was invoked", id);
		return cardRepository.findByUserId(id);
	}

	public void deleteByUserId(Long id) {
		cardRepository.deleteByUserId(id);
		logger.info("Card with userId {} was deleted", id);
	}

}
