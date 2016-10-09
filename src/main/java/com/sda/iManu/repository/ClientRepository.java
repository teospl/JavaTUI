package com.sda.iManu.repository;

import com.sda.iManu.domain.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by teos on 2016-10-03.
 */
public interface ClientRepository extends MongoRepository<Client, String> {

}
