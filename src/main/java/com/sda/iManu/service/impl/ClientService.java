package com.sda.iManu.service.impl;

import com.sda.iManu.domain.Client;
import com.sda.iManu.domain.User;
import com.sda.iManu.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by teos on 2016-10-03.
 */
@Component
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

//    public List<User> getUsers(){
//        List<User> users = clientRepository.findAll();
//        return users;
//    }

}
