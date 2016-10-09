package com.sda.iManu.service.impl;

import com.sda.iManu.domain.Travel;
import com.sda.iManu.dto.RatingDto;
import com.sda.iManu.repository.RatingRepository;
import com.sda.iManu.repository.TravelRepository;
import com.sda.iManu.web.controller.RatingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by teos on 2016-09-29.
 */
@Component
public class TravelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TravelService.class);

    @Autowired
    private TravelRepository travelRepository;


    public boolean saveTravel(Travel travel) {
        travelRepository.save(travel);
        return true;
    }

    public boolean delete(Travel travel) {
        travelRepository.delete(travel);
        return true;
    }

    public boolean saveMainRate(String travelId, Double mainRate) {
        Travel rated = getTravelById(travelId);
        rated.setMainRate(mainRate);
        travelRepository.save(rated);
        return true;
    }

    public List<Travel> getTravels() {
        List<Travel> travels = travelRepository.findAll();
        return travels;
    }

    public Travel getTravelById(String id) {
        Travel travel = travelRepository.findById(id);
        return travel;
    }

    public List<Travel> getTravelsByHotel(String hotel) {
        List<Travel> travels = travelRepository.findAllByHotelContainingIgnoreCase(hotel);
        return travels;
    }

    public List<Travel> getTravelsByCountry(String country) {
        List<Travel> travels = travelRepository.findAllByCountryContainingIgnoreCase(country);
        return travels;
    }

    public List<Travel> getTravelsByCurort(String curort) {
        List<Travel> travels = travelRepository.findAllByCurortContainingIgnoreCase(curort);
        return travels;
    }

    public List<Travel> getTravelsByDescription(String description) {
        List<Travel> travels = travelRepository.findAllByDescriptionContainingIgnoreCase(description);
        return travels;
    }

    public List<Travel> getTravelsByAccess(String access) {
        List<Travel> travels = travelRepository.findAllByAccessContainingIgnoreCase(access);
        return travels;
    }

    public List<Travel> getTravelsByFood(String food) {
        List<Travel> travels = travelRepository.findAllByFoodContainingIgnoreCase(food);
        return travels;
    }

    public List<Travel> getTravelsByDate(String from, String to) {
        List<Travel> travels = travelRepository.findAllByStartDateBetween(from, to);
        return travels;
    }

    public List<Travel> getTravelsByExp(String expression) {
        List<Travel> hotel = getTravelsByHotel(expression);
        List<Travel> country = getTravelsByCountry(expression);
        List<Travel> curort = getTravelsByCurort(expression);
        List<Travel> description = getTravelsByDescription(expression);
        List<Travel> access = getTravelsByAccess(expression);
        List<Travel> food = getTravelsByFood(expression);
        List<Travel> travels = new LinkedList<>();

        int predictedDuplicates = 0;
        if (!hotel.isEmpty()) {
            travels.addAll(hotel);
            predictedDuplicates++;
        }
        if (!country.isEmpty()) {
            travels.addAll(country);
            predictedDuplicates++;
        }
        if (!curort.isEmpty()) {
            travels.addAll(curort);
            predictedDuplicates++;
        }
        if (!description.isEmpty()) {
            travels.addAll(description);
            predictedDuplicates++;
        }
        if (!access.isEmpty()) {
            travels.addAll(access);
            predictedDuplicates++;
        }
        if (!food.isEmpty()) {
            travels.addAll(food);
            predictedDuplicates++;
        }

        return removeDuplicates(travels, predictedDuplicates);
    }

    public List<Travel> getTravelsByDateAndExp(String from, String to, String expression) {
        List<Travel> dateRange = getTravelsByDate(from, to);
        List<Travel> exp = getTravelsByExp(expression);
        List<Travel> travels = new LinkedList<>();
        travels.addAll(dateRange);
        travels.addAll(exp);
        return leaveMatches(travels, 2);
    }

    public List<Travel> getTravelsByCountryAndExp(String selectedCountry, String expression) {
        List<Travel> country = getTravelsByCountry(selectedCountry);
        List<Travel> exp = getTravelsByExp(expression);
        List<Travel> travels = new LinkedList<>();
        travels.addAll(country);
        travels.addAll(exp);
        return leaveMatches(travels, 2);
    }

    public List<Travel> getTravelsByCountryAndAccess(String selectedCountry, String selectedAccess) {
        List<Travel> country = getTravelsByCountry(selectedCountry);
        List<Travel> access = getTravelsByAccess(selectedAccess);
        List<Travel> travels = new LinkedList<>();
        travels.addAll(country);
        travels.addAll(access);
        return leaveMatches(travels, 2);
    }

    public List<Travel> getTravelsByCountryAndAccessAndExp(String selectedCountry, String selectedAccess, String expression) {
        List<Travel> country = getTravelsByCountry(selectedCountry);
        List<Travel> access = getTravelsByAccess(selectedAccess);
        List<Travel> exp = getTravelsByExp(expression);
        List<Travel> travels = new LinkedList<>();
        travels.addAll(country);
        travels.addAll(access);
        travels.addAll(exp);
        return leaveMatches(travels, 3);
    }

    public List<Travel> getTravelsByCountryAndDate(String selectedCountry, String from, String to) {
        List<Travel> country = getTravelsByCountry(selectedCountry);
        List<Travel> dateRange = getTravelsByDate(from, to);
        List<Travel> travels = new LinkedList<>();
        travels.addAll(country);
        travels.addAll(dateRange);
        return leaveMatches(travels, 2);
    }

    public List<Travel> getTravelsByCountryAndDateAndExp(String selectedCountry, String from, String to, String expression) {
        List<Travel> country = getTravelsByCountry(selectedCountry);
        List<Travel> dateRange = getTravelsByDate(from, to);
        List<Travel> exp = getTravelsByExp(expression);
        List<Travel> travels = new LinkedList<>();
        travels.addAll(country);
        travels.addAll(dateRange);
        travels.addAll(exp);
        return leaveMatches(travels, 3);
    }

    public List<Travel> getTravelsByCountryAndAccessAndDate(String selectedCountry, String selectedAccess, String from, String to) {
        List<Travel> country = getTravelsByCountry(selectedCountry);
        List<Travel> access = getTravelsByAccess(selectedAccess);
        List<Travel> dateRange = getTravelsByDate(from, to);
        List<Travel> travels = new LinkedList<>();
        travels.addAll(country);
        travels.addAll(access);
        travels.addAll(dateRange);
        return leaveMatches(travels, 3);
    }

    public List<Travel> getTravelsByCountryAndAccessAndDateAndExp(String selectedCountry, String selectedAccess, String from, String to, String expression) {
        List<Travel> country = getTravelsByCountry(selectedCountry);
        List<Travel> access = getTravelsByAccess(selectedAccess);
        List<Travel> dateRange = getTravelsByDate(from, to);
        List<Travel> exp = getTravelsByExp(expression);
        List<Travel> travels = new LinkedList<>();
        travels.addAll(country);
        travels.addAll(access);
        travels.addAll(dateRange);
        travels.addAll(exp);
        return leaveMatches(travels, 4);
    }

    public List<Travel> getTravelsByAccessAndExp(String selectedAccess, String expression) {
        List<Travel> access = getTravelsByAccess(selectedAccess);
        List<Travel> exp = getTravelsByExp(expression);
        List<Travel> travels = new LinkedList<>();
        travels.addAll(access);
        travels.addAll(exp);
        return leaveMatches(travels, 2);
    }

    public List<Travel> getTravelsByAccessAndDate(String selectedAccess, String from, String to) {
        List<Travel> access = getTravelsByAccess(selectedAccess);
        List<Travel> dateRange = getTravelsByDate(from, to);
        List<Travel> travels = new LinkedList<>();
        travels.addAll(access);
        travels.addAll(dateRange);
        return leaveMatches(travels, 2);
    }

    public List<Travel> getTravelsByAccessAndDateAndExp(String selectedAccess, String from, String to, String expression) {
        List<Travel> access = getTravelsByAccess(selectedAccess);
        List<Travel> dateRange = getTravelsByDate(from, to);
        List<Travel> exp = getTravelsByExp(expression);
        List<Travel> travels = new LinkedList<>();
        travels.addAll(access);
        travels.addAll(dateRange);
        travels.addAll(exp);
        return leaveMatches(travels, 3);
    }

    private List<Travel> removeDuplicates(List<Travel> travelsToClean, int predictedDuplicates) {
        List<Travel> travels = new LinkedList<>(travelsToClean);
        for (int i = 0; i < predictedDuplicates - 1; ++i) {
            for (Travel curr : travelsToClean) {
                int duplicates = 0;
                for (Travel currNow : travelsToClean) {
                    if (currNow.getId().equals(curr.getId())) {
                        duplicates++;
                    }
                    if (duplicates > 1) {
                        travels.remove(currNow);
                    }
                }
            }
        }
        return travels;
    }

    private List<Travel> leaveMatches(List<Travel> travelsToMatch, int numberToMach) {
        List<Travel> travels = new LinkedList<>(travelsToMatch);

        for (Travel firstLayerCurrent : travelsToMatch) {
            int duplicates = 0;
            for (Travel secondLayerCurrent : travelsToMatch) {
                if (secondLayerCurrent.getId().equals(firstLayerCurrent.getId())) {
                    duplicates++;
                }
            }
            if (duplicates < numberToMach) {
                travels.remove(firstLayerCurrent);
            }
        }
        return removeDuplicates(travels, numberToMach);
    }

}
