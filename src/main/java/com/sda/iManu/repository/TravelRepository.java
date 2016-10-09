package com.sda.iManu.repository;

import com.sda.iManu.domain.Travel;
import com.sda.iManu.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by teos on 2016-09-29.
 */
public interface TravelRepository extends MongoRepository<Travel, String> {

    Travel findById(String id);

    List<Travel> findAllByHotelContainingIgnoreCase(String hotel);

    List<Travel> findAllByCountryContainingIgnoreCase(String country);

    List<Travel> findAllByCurortContainingIgnoreCase(String curort);

    List<Travel> findAllByDescriptionContainingIgnoreCase(String description);

    List<Travel> findAllByAccessContainingIgnoreCase(String access);

    List<Travel> findAllByFoodContainingIgnoreCase(String food);

    List<Travel> findAllByStartDateBetween(String from, String to);

//    List<Travel> findAllBy(String expression);

}
