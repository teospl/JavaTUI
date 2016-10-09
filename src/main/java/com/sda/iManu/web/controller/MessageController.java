package com.sda.iManu.web.controller;

import com.sda.iManu.converter.MessageDtoToMessageConverter;
import com.sda.iManu.domain.Message;
import com.sda.iManu.domain.Travel;
import com.sda.iManu.dto.ClientDto;
import com.sda.iManu.dto.MessageDto;
import com.sda.iManu.dto.TravelDto;
import com.sda.iManu.service.SecUserDetails;
import com.sda.iManu.service.impl.MessageService;
import com.sda.iManu.service.impl.TravelService;
import com.sda.iManu.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sda.iManu.dto.MessageDto.fromMessage;
import static com.sda.iManu.dto.TravelDto.fromTravel;

/**
 * Created by teos on 2016-10-03.
 */
@Controller
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);


    private MessageDtoToMessageConverter converter
            = new MessageDtoToMessageConverter();

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;
    @Autowired
    TravelService travelService;

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public ModelAndView getMessages(@ModelAttribute MessageDto messageDto) {
        LOGGER.info("Wywołanie messages");
        String userId = (((SecUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).userData).getId();
        LinkedList<MessageDto> messages = new LinkedList<>();
        List<MessageDto> messagesPre = messageService
                .getMessageByTo(userId)
                .stream()
                .map(MessageDto::fromMessage)
                .collect(Collectors.toCollection(LinkedList::new));
        for(MessageDto current: messagesPre){
            String id = current.getTravelId();
            Travel travel = travelService.getTravelById(id);
            TravelDto travelDto = fromTravel(travel);
            String travelName = travelDto.getTravelName();
            current.setTravelName(travelName);
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
                String fromName = (((SecUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).userData).getFirstName() + " " +
                        (((SecUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).userData).getLastName();
                current.setFromName(fromName);
            }
            messages.add(current);
        }
        LOGGER.info("Messages by id list: {}", messages);


        Collections.reverse(messages);
        return new ModelAndView("messages").addObject("messageList", messages);

    }


    @RequestMapping(value = "/message/new", method = RequestMethod.GET)
    public ModelAndView newMessage(@RequestParam String userId,@RequestParam String travelId, ModelMap model) {
        LOGGER.info("Wywolanie GET na message/new");
        ModelAndView modelAndView = prepareModelAndView();
        MessageDto messageDto = new MessageDto();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        messageDto.setDate(dateFormat.format(cal.getTime()));
        if(!("null").equals(travelId) && !("").equals(userId)){
            messageDto.setTravelId(travelId);
        }
        if (!("null").equals(userId) && !("").equals(userId)){
            messageDto.setTo(userId);
        }
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            String fromName = (((SecUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).userData).getFirstName() + " " +
                    (((SecUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).userData).getLastName();
            String from = (((SecUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).userData).getId();
            messageDto.setFromName(fromName);
            messageDto.setFrom(from);
        }

        modelAndView.addObject("data", messageDto);
        modelAndView.setViewName("newMessage");
        return modelAndView;
    }

    @RequestMapping(value = "/message/new", method = RequestMethod.POST)
    public String sendMessage(
            @ModelAttribute(value = "data") @Valid MessageDto messageDto,
            BindingResult result,
            ModelMap model) {
        LOGGER.info("Wywołanie post na message/new");

        if (result.hasErrors()) {
            LOGGER.info("error: {}", result.getAllErrors());
            return "error";
        } else {

            if (messageService.saveMessage(converter.convert(messageDto))) {
                LOGGER.info("New Travel created: {}", messageDto);
                return "redirect:/messages";
            } else {
                LOGGER.info("cannot send Message");
                return "newMessage";
            }
        }
    }

    @RequestMapping(value = "/message/getMessage", method = RequestMethod.GET)
    public ModelAndView getMessageDetails(@RequestParam String messageId) {
        LOGGER.info("--> Wywołanie message by ID");
        Message messagePre = messageService.getMessageById(messageId);
        MessageDto message = fromMessage(messagePre);
        Travel travel = travelService.getTravelById(messagePre.getTravelId());
        TravelDto travelDto = fromTravel(travel);
        String travelName = travelDto.getTravelName();
        message.setTravelName(travelName);
//        String travelId = messagePre.getTravelId();
//        Travel travel = travelService.getTravelById(travelId);
//        String travelName = travel.getTravelName;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            String fromName = (((SecUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).userData).getFirstName() + " " +
                    (((SecUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).userData).getLastName();
            message.setFromName(fromName);
        }

        LOGGER.info("--> Znaleziona podroz: {}", message);
        return new ModelAndView("message").addObject("message", message); //.addObject("travelName", travelName);

    }

    private ModelAndView prepareModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        String role = (((SecUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).userData).getRole().toString();
        LinkedList<ClientDto> possibleUsers = new LinkedList<>();
        List<ClientDto> users = userService
                .getUsers()
                .stream()
                .map(ClientDto::fromUser)
                .collect(Collectors.toCollection(LinkedList::new));
        LinkedList<TravelDto> possibleTravels = new LinkedList<>();
        List<TravelDto> travels = travelService.getTravels().stream().map(TravelDto::fromTravel).collect(Collectors.toCollection(LinkedList::new));

        if (role.equals("ADMIN")) {
            for (ClientDto current : users) {
                possibleUsers.add(current);
            }
            for (TravelDto currTravel : travels ){
                possibleTravels.add(currTravel);
            }
        } else if (role.equals("USER")) {
            for (ClientDto current : users) {
                if (current.getRole().toString().equals("ADMIN")) {
                    possibleUsers.add(current);
                }
            }
        }
        modelAndView.addObject("possibleUsers", possibleUsers);
        modelAndView.addObject("possibleTravels", possibleTravels);
        LOGGER.info("prepareModelAndView nowej wiadomości, lista users: ", possibleUsers);
        return modelAndView;
    }
}



