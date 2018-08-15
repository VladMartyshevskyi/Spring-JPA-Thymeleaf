package com.kemal.spring.web.controllers.viewControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.kemal.spring.domain.Review;
import com.kemal.spring.service.ReviewService;

@Controller
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@GetMapping("/review")
	public String producerForm(Model model) {
		Review review = new Review();
		model.addAttribute("review", review);
		return "website/review";
	}
	
	@PostMapping("/review/new")
	public String newReview(@ModelAttribute("review") Review review) {
		reviewService.save(review);
		return "redirect:/index";
	}
}
