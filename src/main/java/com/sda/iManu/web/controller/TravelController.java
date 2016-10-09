package com.sda.iManu.web.controller;

import com.sda.iManu.converter.TravelDtoToTravelConverter;
import com.sda.iManu.domain.*;
import com.sda.iManu.dto.RatingDto;
import com.sda.iManu.dto.SearchDto;
import com.sda.iManu.dto.TravelDto;
import com.sda.iManu.service.impl.RatingService;
import com.sda.iManu.service.impl.TravelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by teos on 2016-09-29.
 */

@Controller
public class TravelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TravelController.class);


    private TravelDtoToTravelConverter converter
            = new TravelDtoToTravelConverter();

    @Autowired
    private TravelService travelService;

    @Autowired
    private RatingService ratingService;


    @RequestMapping(value = "/travel/getTravel", method = RequestMethod.GET)
    public ModelAndView getStoreDetails(@RequestParam String travelId) {
        LOGGER.info("--> Wywołanie travel by ID \077");
        Travel travel = travelService.getTravelById(travelId);
        LOGGER.info("--> Znaleziona podroz: {}", travel);
        Double mainRate = 0.0;
        RatingDto ratingDto = new RatingDto();
        ratingDto.setTravelId(travelId);
        DecimalFormat df = new DecimalFormat("#.##");

        //ratings
        List<RatingDto> ratings = ratingService
                .getRatingByTravelId(travelId)
                .stream()
                .map(RatingDto::fromRatings)
                .collect(Collectors.toCollection(LinkedList::new));
        Collections.reverse(ratings);

//        for (RatingDto current : ratings) {
//            mainRate += Double.valueOf(current.getRating());
//        }
//        LOGGER.info("-> obliczanie sredniej oceny {}", mainRate);
//        mainRate /= ratings.size();
//            travel.setMainRate( mainRate);
        return new ModelAndView("travel").addObject("travel", travel).addObject("data", ratingDto).addObject("ratingsList", ratings);
        // return travel;
    }

    @RequestMapping(value = "/travels", method = RequestMethod.GET)
    public ModelAndView getTravels(@ModelAttribute TravelDto travelDto) {
        LOGGER.info("Wywołanie travels");
        List<TravelDto> travels = travelService
                .getTravels()
                .stream()
                .map(TravelDto::fromTravel)
                .collect(Collectors.toCollection(LinkedList::new));
        LOGGER.info("Travels list: {}", travels);
        return new ModelAndView("travels").addObject("travelList", travels);

    }

    @RequestMapping(value = "/travel/delete", method = RequestMethod.GET)
    public String delete(
            @ModelAttribute(value = "data") @Valid TravelDto travelDto, @RequestParam String travelId,
            BindingResult result,
            ModelMap model) {
        LOGGER.info("Wywołanie post na travel/delete, przekazany obiekt: {}", travelDto);

        if (result.hasErrors()) {
            LOGGER.info("error: {}", result.getAllErrors());
            return "error";
        } else {
            travelDto.setId(travelId);
            if (travelService.delete(converter.convert(travelDto))) {
                LOGGER.info("Travel deleted: {}", travelDto);
                return "redirect:/travels";
            } else {
                LOGGER.info("cannot delete travel");
                return "redirect:/travel/getTravel?travelId=";
            }
        }
    }

    @RequestMapping(value = "/travel/edit", method = RequestMethod.POST)
    public String edit(
            @ModelAttribute(value = "data") @Valid TravelDto travelDto, @RequestParam String travelId,
            BindingResult result,
            ModelMap model) {
        LOGGER.info("Wywołanie post na travel/edit, przekazany obiekt: {}", travelDto);

        if (result.hasErrors()) {
            LOGGER.info("error: {}", result.getAllErrors());
            return "error";
        } else {
            travelDto.setId(travelId);
            if (travelService.saveTravel(converter.convert(travelDto))) {
                LOGGER.info("Travel edited: {}", travelDto);
                return "redirect:/travel/getTravel?travelId=" + travelId;
            } else {
                LOGGER.info("cannot edit travel");
                return "redirect:/travel/getTravel?travelId=";
            }
        }
    }

    @RequestMapping(value = "/travel/edit", method = RequestMethod.GET)
    public ModelAndView getEditDetails(@RequestParam String travelId) {
        LOGGER.info("--> Wywołanie travel edit by ID \077");

        ModelAndView editTravel = prepareModelAndView();
        Travel travel = travelService.getTravelById(travelId);
        LOGGER.info("--> Znaleziona podroz: {}", travel);
        editTravel.setViewName("editTravel");
        return editTravel.addObject("travel", travel);
    }

    @RequestMapping(value = "/travel/new", method = RequestMethod.POST)
    public String register(
            @ModelAttribute(value = "data") @Valid TravelDto travelDto,
            BindingResult result,
            ModelMap model) {
        LOGGER.info("Wywołanie post na travel/new");

        if (result.hasErrors()) {
            LOGGER.info("error: {}", result.getAllErrors());
            return "error";
        } else {

            if (travelService.saveTravel(converter.convert(travelDto))) {
                LOGGER.info("New Travel created: {}", travelDto);
                return "redirect:/travels";
            } else {
                LOGGER.info("cannot add travel");
                return "addTravel";
            }
        }
    }

    @RequestMapping(value = "/travel/new", method = RequestMethod.GET)
    public ModelAndView startTravelProcess(ModelMap model) {
        LOGGER.info("Wywolanie GET na travel/new");
        ModelAndView modelAndView = prepareModelAndView();
        modelAndView.addObject("data", new TravelDto());
        modelAndView.setViewName("addTravel");
        return modelAndView;
    }

    private ModelAndView prepareModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        LinkedList<Access> accesses = new LinkedList<>();
        LinkedList<Country> countries = new LinkedList<>();
        LinkedList<Food> foods = new LinkedList<>();
        LinkedList<Stay> stays = new LinkedList<>();
        for (Access current : Access.values()) {
            accesses.add(current);
        }
        for (Country current : Country.values()) {
            countries.add(current);
        }
        for (Food current : Food.values()) {
            foods.add(current);
        }
        for (Stay current : Stay.values()) {
            stays.add(current);
        }
        modelAndView.addObject("countries", countries);
        modelAndView.addObject("accesses", accesses);
        modelAndView.addObject("stays", stays);
        modelAndView.addObject("foods", foods);
        return modelAndView;
    }

}
