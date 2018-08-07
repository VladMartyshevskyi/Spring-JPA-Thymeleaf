package com.kemal.spring.domain.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.kemal.spring.domain.Card;
import com.kemal.spring.domain.CardRepository;
import com.kemal.spring.domain.CardType;
import com.kemal.spring.service.CardService;

@RunWith(MockitoJUnitRunner.class)
public class CardServiceTest {
	
	@Mock
	private CardRepository cardRepository;
	
	@InjectMocks
	private CardService cardService;

	@Test
	public void testGetCardTypess() {
		assertEquals(CardType.VISA, cardService.getCardType("4858232134331"));
		assertEquals(CardType.MASTERCARD, cardService.getCardType("5372873528313"));
		assertEquals(CardType.UNKNOWN, cardService.getCardType("197362675813"));
	}
	
	
	@Test
	public void testUpdate() {
		Card card = new Card();
		when(cardRepository.findById(anyLong())).thenReturn(card);
		
		Card updatedCard = new Card();
		updatedCard.setNumber("489273982");
		cardService.update(updatedCard);
		verify(cardRepository).save(updatedCard);
	}
	
	@Test(expected = RuntimeException.class)
	public void testUpdateWithException() {
		when(cardRepository.findById(anyLong())).thenReturn(null);
		Card updatedCard = new Card();
		updatedCard.setNumber("489273982");
		cardService.update(updatedCard);
	}
	 
}
