package com.sda.iManu.service.impl;

import com.sda.iManu.domain.Travel;
import com.sda.iManu.repository.SearchRepository;
import com.sda.iManu.repository.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by teos on 2016-10-06.
 */
@Component
public class SearchService {

    @Autowired
    TravelRepository travelRepository;

    public List<Travel> getAllByCountryIgnoreCase(String country){
        List<Travel> travels = travelRepository.findAllByCountryContainingIgnoreCase(country);
        return travels;
    }
}
