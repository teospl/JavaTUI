package com.sda.iManu.repository;

import com.sda.iManu.domain.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by teos on 2016-10-01.
 */
public interface RatingRepository extends MongoRepository<Rating, String> {
    Rating findByUserId(String id);

    List<Rating> findAllByTravelId(String id);


}
