package com.sda.iManu.service.impl;

import com.sda.iManu.domain.User;
import com.sda.iManu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by klolo on 27.09.16.
 */
@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean registerUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        User saved = userRepository.save(user);
        return saved != null;
    }

    public List<User> getUsers(){
        List<User> users = userRepository.findAll();
        return users;
    }

    public User getUserById(String id){
        User user = userRepository.findById(id);
        return user;
    }

    public boolean delete(User user) {
        userRepository.delete(user);
        return true;
    }
    public boolean saveUser(User user) {
        userRepository.save(user);
        return true;
    }
}
