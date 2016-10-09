package com.sda.iManu.service;

import com.sda.iManu.domain.User;
import com.sda.iManu.repository.UserRepository;
import com.sda.iManu.web.controller.HomeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * This implementation allows use mongo with Spring Security.
 */
@Component
public class SecUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Methods allows login user. It finds user in repository by login.
     *
     * Example of inserting new user to database:
     * userRepository.save(new User("Kamil","Lolo","lolcio",new BCryptPasswordEncoder().encode("lolcio123")))
     */
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        LOGGER.info("Try to login user: {}", username);
        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        else {
            return new SecUserDetails(user);
        }
    }
}
