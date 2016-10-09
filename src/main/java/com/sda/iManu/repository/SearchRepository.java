package com.sda.iManu.repository;

import com.sda.iManu.domain.Travel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by teos on 2016-10-06.
 */
public interface SearchRepository extends MongoRepository<Travel, String> {

    List<Travel> findAllByCountry(String country);

    List<Travel> findAllByAccess(String access);
}
