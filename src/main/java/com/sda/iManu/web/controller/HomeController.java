package com.sda.iManu.web.controller;


import com.sda.iManu.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/home")
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @SuppressWarnings("unused")
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String printHello(ModelMap model) {
        LOGGER.info("start");
        model.addAttribute("message", "Hello Spring MVC Framework!");
        model.addAttribute("recipient", "World");
        return "home";
    }

}
