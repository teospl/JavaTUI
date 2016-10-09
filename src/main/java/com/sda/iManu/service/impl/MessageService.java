package com.sda.iManu.service.impl;

import com.sda.iManu.domain.Message;
import com.sda.iManu.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by teos on 2016-10-03.
 */
@Component
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public boolean saveMessage(Message message) {
        messageRepository.save(message);
        return true;
    }

    public List<Message> getMessageByFrom(String id){
        List<Message> messages = messageRepository.findAllByFrom(id);
        return messages;
    }

    public List<Message> getMessageByTo(String id){
        List<Message> messages = messageRepository.findAllByTo(id);
        return messages;
    }

    public Message getMessageById(String id){
        Message message = messageRepository.findById(id);
        return message;
    }

    public List<Message> getMessageByToAndTravelId(String to, String travelId){
        List<Message> messages = messageRepository.findAllByToAndTravelId(to, travelId);
        return messages;
    }

}
