package com.kemal.spring.web.controllers.viewControllers;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
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
    	OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
        Authentication authentication = oAuth2Authentication.getUserAuthentication();
        Map<String, String> authenticationDetails = (LinkedHashMap<String, String>) authentication.getDetails();
        String username = authenticationDetails.get("name");
       // userService.createFacebookUser(principal.getName());

    	return "redirect:/index";
    }
    
    
}
