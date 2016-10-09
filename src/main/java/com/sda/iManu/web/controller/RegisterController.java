package com.sda.iManu.web.controller;

import com.sda.iManu.converter.RegistrationDtoToUserConverter;
import com.sda.iManu.domain.Role;
import com.sda.iManu.domain.Stay;
import com.sda.iManu.dto.RegistrationDto;
import com.sda.iManu.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.LinkedList;

/**
 * Kontroller obslugujacy logowanie uzytkownika do aplikacji.
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);


    private RegistrationDtoToUserConverter converter
            = new RegistrationDtoToUserConverter();

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public String register(
            @ModelAttribute(value = "data") @Valid RegistrationDto registrationDto,
            BindingResult result,
            ModelMap model) {
        LOGGER.info("start");

        registrationDto.setRole(Role.USER);
        if (result.hasErrors()) {
            LOGGER.info("error: {}", result.getAllErrors());
            return "register";
        } else {
            if (userService.registerUser(converter.convert(registrationDto))) {
                LOGGER.info("user created: {}", registrationDto);
                return "login";
            } else {
                LOGGER.info("cannot add user");
                return "register";
            }
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView startRegisterProcess(ModelMap model) {
        LOGGER.info("start");
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("data", new RegistrationDto());
        modelAndView.setViewName("register");
        return modelAndView;
    }
}
