package com.kemal.spring.web.controllers.viewControllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.kemal.spring.domain.Card;
import com.kemal.spring.service.CardService;
import com.kemal.spring.service.UserService;


@Controller
public class CardController {

	@Autowired
	private CardService cardService;

	@Autowired
	private UserService userService;

	@GetMapping("/cards")
	public String showCards(Model model, Principal principal) {
		List<Card> cards = cardService.findByUserId(userService.findByUsername(principal.getName()).getId());
		model.addAttribute("cards", cards);
		return "website/cards";
	}


	@DeleteMapping("/cards/delete/{id}")
	public ResponseEntity deleteCard(@PathVariable Long id) {
		cardService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/cards/new")
	public String getNewCardForm(Model model) {
		model.addAttribute("card", new Card());
		return "website/newCard";
	}

	@PostMapping("/cards/new")
	public String addCard(@ModelAttribute("card") @Valid final Card card, Principal principal) {
		card.setUserId(userService.findByUsername(principal.getName()).getId()); 
		cardService.save(card);
		return "redirect:/cards";
	}

	@GetMapping("/cards/update/{id}")
	public String getCardUpdateForm(Model model, @PathVariable Long id) {
		Card card = cardService.findById(id);
		model.addAttribute("card", card);
		return "website/editCard";
	}
	
	@PostMapping("/cards/update/{id}")
	public String updateCard(@ModelAttribute("card") @Valid final Card card) {
		cardService.update(card);
		return "redirect:/cards";
	}

}
