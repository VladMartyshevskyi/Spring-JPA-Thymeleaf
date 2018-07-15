package com.kemal.spring.web.controllers.viewControllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import com.kemal.spring.service.UserService;
import com.kemal.spring.web.dto.UserDto;

@Controller
public class RegisterController {
	
	@Autowired
    private UserService userService;

    @PostMapping(value = "/submit-registration")
    public String saveUser(@ModelAttribute("userDto") @Valid final UserDto userDto){
    	userService.createUser(userDto);
        return "website/registered";
    }
    
    @GetMapping(value = "/register")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "website/register";
    }
}
