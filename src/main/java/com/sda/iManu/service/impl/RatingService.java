package com.sda.iManu.service.impl;

import com.sda.iManu.domain.Rating;
import com.sda.iManu.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.StreamSupport;

/**
 * Created by teos on 2016-10-01.
 */
@Component
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public List<Rating> getRatingByTravelId(String id){
        List<Rating> rating = ratingRepository.findAllByTravelId(id);
        return rating;
    }


    public boolean saveRating(Rating rating) {
        ratingRepository.save(rating);
        return true;
    }


}
