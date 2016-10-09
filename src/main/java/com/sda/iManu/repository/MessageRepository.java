package com.sda.iManu.repository;

import com.sda.iManu.domain.Message;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by teos on 2016-10-03.
 */
public interface MessageRepository extends MongoRepository<Message, String> {

    List<Message> findAllByFrom(String id);

    List<Message> findAllByTo(String id);

    List<Message> findAllByToAndTravelId(String to, String travelId);

    Message findById(String id);
}
