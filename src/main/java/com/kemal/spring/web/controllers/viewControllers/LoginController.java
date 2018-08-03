package com.kemal.spring.web.controllers.viewControllers;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kemal.spring.service.UserService;

@Controller
@RequestMapping("")
public class LoginController {

	@Autowired
	private UserService userService;
    
	@GetMapping(value = "/login")
    public String login (){
        return "website/login";
    }
	
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "website/login";
    }
    
    @GetMapping("/facebook-success")
    public String facebookSuccess(Principal principal) {
    	userService.authenticateSocialUser(principal);
    	return "redirect:/index";
    }
    
    
}
