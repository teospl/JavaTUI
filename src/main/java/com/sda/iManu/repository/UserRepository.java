package com.sda.iManu.repository;

import com.sda.iManu.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by kamil on 18.09.16.
 */
public interface UserRepository extends MongoRepository<User, String> {


    User findByFirstName(String firstName);

    User findByLogin(String login);
    User findById(String id);

    List<User> findByLastName(String lastName);


}
