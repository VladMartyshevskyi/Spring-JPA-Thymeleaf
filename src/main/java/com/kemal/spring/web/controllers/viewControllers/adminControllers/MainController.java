package com.kemal.spring.web.controllers.viewControllers.adminControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	
	@GetMapping("/adminPage")
    public ModelAndView adminPage(){
        return new ModelAndView("redirect:/adminPage/users");
    }
}
