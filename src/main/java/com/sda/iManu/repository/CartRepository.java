package com.sda.iManu.repository;

import com.sda.iManu.domain.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by teos on 2016-10-05.
 */
public interface CartRepository extends MongoRepository<Cart, String> {
    Cart findById(String id);
//    Cart findByTravelId(String travelId);
    List<Cart> findAllByUserId(String id);
}
