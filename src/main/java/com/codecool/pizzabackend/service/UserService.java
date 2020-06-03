package com.codecool.pizzabackend.service;

import com.codecool.pizzabackend.controller.PizzaController;
import com.codecool.pizzabackend.entity.User;
import com.codecool.pizzabackend.repository.UserRepository;
import com.codecool.pizzabackend.security.UserCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        LOGGER.info(username);
//        UserCredential foundUser = userRepository.findUserByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
//        LOGGER.info(String.format("User found. User info: %s", foundUser));

        LOGGER.info(username);
        UserCredential foundUser = userRepository.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
        LOGGER.info(String.format("User found. User info: %s", foundUser));

//        User foundUser = new User("customer",
//                 passwordEncoder.encode("pass"),
//                "customer",
//                "Customer",
//                null, true,
//                true,
//                true,
//                true);
        return foundUser;
    }
}
