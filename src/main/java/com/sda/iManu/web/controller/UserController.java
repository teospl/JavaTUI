package com.sda.iManu.web.controller;

import com.sda.iManu.converter.UserDtoToUserConverter;
import com.sda.iManu.domain.Role;
import com.sda.iManu.domain.User;
import com.sda.iManu.dto.UserDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by teos on 2016-10-07.
 */
@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    private UserDtoToUserConverter converter
            = new UserDtoToUserConverter();

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView getUsers(@ModelAttribute UserDto userDto) {
        LOGGER.info("Wywołanie users");
        List<UserDto> users = userService
                .getUsers()
                .stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toCollection(LinkedList::new));
        LOGGER.info("Users list: {}", users);
        return new ModelAndView("users").addObject("userList", users);

    }

    @RequestMapping(value = "/user/delete", method = RequestMethod.GET)
    public String delete(
            @ModelAttribute(value = "data") @Valid UserDto userDto, @RequestParam String userId,
            BindingResult result,
            ModelMap model) {
        LOGGER.info("Wywołanie post na user/delete, przekazany obiekt: {}", userDto);

        if (result.hasErrors()) {
            LOGGER.info("error: {}", result.getAllErrors());
            return "error";
        } else {
            userDto.setId(userId);
            if (userService.delete(converter.convert(userDto))) {
                LOGGER.info("User deleted: {}", userDto);
                return "redirect:/users";
            } else {
                LOGGER.info("cannot delete user");
                return "redirect:/user/edit?userId=";
            }
        }
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public String edit(
            @ModelAttribute(value = "data") @Valid UserDto userDto, @RequestParam String userId,
            BindingResult result,
            ModelMap model) {
        LOGGER.info("Wywołanie post na user/edit, przekazany obiekt: {}", userDto);

        if (result.hasErrors()) {
            LOGGER.info("error: {}", result.getAllErrors());
            return "error";
        } else {
            userDto.setId(userId);
            if (userService.saveUser(converter.convert(userDto))) {
                LOGGER.info("User edited: {}", userDto);
                return "redirect:/users";
            } else {
                LOGGER.info("cannot edit user");
                return "redirect:/users";
            }
        }
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
    public ModelAndView getEditDetails(@RequestParam String userId) {
        LOGGER.info("Wywołanie user edit by ID");
        ModelAndView editUser = new ModelAndView();
        LinkedList<Role> roles = new LinkedList<>();
        for (Role current : Role.values()) {
            roles.add(current);
        }
        editUser.addObject("roles", roles);
        User user = userService.getUserById(userId);
        LOGGER.info("--> Znaleziona podroz: {}", user);
        editUser.setViewName("editUser");
        return editUser.addObject("data", user);
    }



}
