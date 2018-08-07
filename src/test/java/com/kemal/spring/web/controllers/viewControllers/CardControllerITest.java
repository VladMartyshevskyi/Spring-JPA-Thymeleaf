package com.kemal.spring.web.controllers.viewControllers;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.kemal.spring.domain.Card;
import com.kemal.spring.domain.CardType;
import com.kemal.spring.service.CardService;
import com.kemal.spring.service.UserService;
import com.kemal.spring.web.dto.UserDto;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest
public class CardControllerITest {

	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDetailsService userDetailsService;

	private Long userId;
	private UserDetails userDetails;
	
	private MockMvc mvc;
	
	@Before
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
		
		// Creating test user and cards
		userService.createUser(new UserDto("test", "test", "test", "test@test.com", "test"));
		userId = userService.findByUsername("test").getId();
		userDetails = userDetailsService.loadUserByUsername("test@test.com");
		cardService.updateCardTypeAndsave(new Card("489726873687", 111, "Privat", 12, 2019, userId));
		cardService.updateCardTypeAndsave(new Card("543642423127", 121, "OTP", 8, 2020, userId));
	}

	@Test
	public void testShowCards() throws Exception {
		  mvc.perform(get("/cards/").with(user(userDetails)))
		  .andExpect(status().isOk())
          .andExpect(model().attribute("cards", hasSize(2)))
          .andExpect(model().attribute("cards", hasItem(
                 allOf(
                         hasProperty("number", is("489726873687")),
                         hasProperty("cvv", is(111)),
                         hasProperty("bank", is("Privat")),
                         hasProperty("expiresMonth", is(12)),
                         hasProperty("expiresYear", is(2019)),
                         hasProperty("userId", is(userId)),
                         hasProperty("cardType", is(CardType.VISA))
                 )
         )))
          .andExpect(model().attribute("cards", hasItem(
                  allOf(
                          hasProperty("number", is("543642423127")),
                          hasProperty("cvv", is(121)),
                          hasProperty("bank", is("OTP")),
                          hasProperty("expiresMonth", is(8)),
                          hasProperty("expiresYear", is(2020)),
                          hasProperty("userId", is(userId)),
                          hasProperty("cardType", is(CardType.MASTERCARD))
                  )
          )));
	}

	@Test
	public void testAddCard() throws Exception {
		fail("Not yet implemented");
				
	}
	
	@Test
	public void testDeleteCard() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateCard() {
		fail("Not yet implemented");
	}
	
	@After
	public void cleanUp() {
		userService.delete(userId);
	}

}
