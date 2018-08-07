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
import com.kemal.spring.domain.CardType;
import com.kemal.spring.web.controllers.viewControllers.CardController;

@Service
public class CardService {

	private static Logger logger = LoggerFactory.getLogger(CardService.class);
	private static final String VISA_FIRST_NUMBER = "4";
	private static final String MASTERCARD_FIRST_NUMBER = "5";
	@Autowired
	private CardRepository cardRepository;

	/**
	 * Updates card type (depends on the number) and saves card to repository
	 * @param card
	 */
	public void updateCardTypeAndsave(Card card) {
		card.setCardType(getCardType(card.getNumber()));
		cardRepository.save(card);
		logger.info("Card {} was saved", card);
	}

	/**
	 * Deletes card by id
	 * @param id
	 */
	@Transactional
	@PreAuthorize("@SecurityService.isCardOwner(authentication, #id) or hasRole('ROLE_ADMIN')")
	public void deleteById(Long id) {
		cardRepository.deleteById(id);
		logger.info("Card with id {} was deleted", id);
	}
    /**
     * Finds card by id
     * @param id
     * @return found card
     */
	@Transactional
	@PreAuthorize("@SecurityService.isCardOwner(authentication, #id) or hasRole('ROLE_ADMIN')")
	public Card findById(Long id) {
		logger.debug("Method findById with id {} was invoked", id);
		return cardRepository.findById(id);
	}

	/**
	 * Updates card if exists, otherwise throws RuntimeException
	 * @param card
	 */
	@Transactional
	@PreAuthorize("@SecurityService.isCardOwner(authentication, #card) or hasRole('ROLE_ADMIN')")
	public void update(Card card) {
		logger.debug("Method update was invoked with card: {}", card);
		if (cardRepository.findById(card.getId()) != null) {
			updateCardTypeAndsave(card);
			logger.info("Card was updated : {} ", card);
		} else {
			logger.error("Card with id {} is not found to be updated", card.getId());
			throw new RuntimeException("Card with id " + card.getId() + " is not found to be updated");
		}
	}
	/**
	 * Finds card by user Id
	 * @param id
	 * @return
	 */
	public List<Card> findByUserId(Long id) {
		logger.debug("Method findByUserId with id {} was invoked", id);
		return cardRepository.findByUserId(id);
	}

	/**
	 * Deletes card by user id
	 * @param id
	 */
	public void deleteByUserId(Long id) {
		logger.info("Method deleteByUserId id {} was invoked", id);
		cardRepository.deleteByUserId(id);
		logger.info("Card with userId {} was deleted", id);
	}

	/**
	 * Determines a card type depends on the number
	 * @param number
	 * @return
	 */
	public CardType getCardType(String number) {
		if (number.startsWith(VISA_FIRST_NUMBER)) {
			return CardType.VISA;
		} else if (number.startsWith(MASTERCARD_FIRST_NUMBER)) {
			return CardType.MASTERCARD;
		}
		return CardType.UNKNOWN;
	}

}
