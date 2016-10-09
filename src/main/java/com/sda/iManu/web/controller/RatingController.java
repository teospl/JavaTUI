package com.sda.iManu.web.controller;

import com.sda.iManu.converter.RatingDtoToRatingConverter;
import com.sda.iManu.dto.RatingDto;
import com.sda.iManu.dto.TravelDto;
import com.sda.iManu.service.SecUserDetails;
import com.sda.iManu.service.impl.RatingService;
import com.sda.iManu.service.impl.TravelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.*;
import javax.validation.Valid;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by teos on 2016-10-01.
 */
@Controller
public class RatingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RatingController.class);


    private RatingDtoToRatingConverter converter
            = new RatingDtoToRatingConverter();

    @Autowired
    private RatingService ratingService;
    @Autowired
    private TravelService travelService;


    @RequestMapping(value = "/travel/rate", method = RequestMethod.POST)
    public String register(@ModelAttribute(value = "data") @Valid RatingDto ratingDto,
            BindingResult result,
            ModelMap model) {
        LOGGER.info("Wywołanie post na travel/rate");
        String userName = (((SecUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).userData).getFirstName() + " " + (((SecUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).userData).getLastName();

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser") {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            LOGGER.info("error: {}", result.getAllErrors());
            return "error";
        } else {
            ratingDto.setUserId(userName);
            if (ratingService.saveRating(converter.convert(ratingDto))) {
                // changing main rate of object
                updateMainRate(ratingDto);
                /// end
                LOGGER.info("New rate created: {}", ratingDto);
                return "redirect:/travel/getTravel?travelId=" + ratingDto.getTravelId();
            } else {
                LOGGER.info("cannot add travel");
                return "redirect:/travel/getTravel?travelId=" + ratingDto.getTravelId();
            }
        }
    }

    @RequestMapping(value = "/travel/rate", method = RequestMethod.GET)
    public ModelAndView startRatingProcess(@RequestParam String travelId, ModelMap model) {
        LOGGER.info("Wywolanie GET na travel/rate");
        ModelAndView modelAndView = new ModelAndView();
        RatingDto ratingDto = new RatingDto();
        ratingDto.setTravelId(travelId);
        modelAndView.addObject("data",ratingDto );

        modelAndView.setViewName("rateTravel");
        return modelAndView;
    }

    @RequestMapping(value = "/ratings", method = RequestMethod.GET)
    public ModelAndView getRatings(@RequestParam String travelId, @ModelAttribute RatingDto ratingDto) {
        LOGGER.info("Wywołanie listy ocen");
        List<RatingDto> ratings = ratingService
                .getRatingByTravelId(travelId)
                .stream()
                .map(RatingDto::fromRatings)
                .collect(Collectors.toCollection(LinkedList::new));
        Collections.reverse(ratings);

        LOGGER.info("Ratings list: {}", ratings);
        return new ModelAndView("ratings").addObject("ratingsList", ratings);

    }

    private void updateMainRate(RatingDto ratingDto){
        Double mainRate = 0.0;
        List<RatingDto> ratings = ratingService
                .getRatingByTravelId(ratingDto.getTravelId())
                .stream()
                .map(RatingDto::fromRatings)
                .collect(Collectors.toCollection(LinkedList::new));
        Collections.reverse(ratings);

        for (RatingDto current : ratings) {
            mainRate += Double.valueOf(current.getRating());
        }
        LOGGER.info("obliczanie sredniej oceny {}", mainRate);
        mainRate /= ratings.size();
        travelService.saveMainRate(ratingDto.getTravelId(), mainRate);
    }

}
