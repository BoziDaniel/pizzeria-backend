package com.codecool.pizzabackend.controller;

import com.codecool.pizzabackend.entity.Customer;
import com.codecool.pizzabackend.entity.UserCredentials;
import com.codecool.pizzabackend.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private AuthService authService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserCredentials data) {
        LOGGER.info(String.format("Post request: /login arrived. Username: %s", data.getUsername()));
        try {
            LOGGER.info(String.format("Post request: /login processed. Username: %s", data.getUsername()));
            return authService.login(data);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("/registration")
    public void registration(@RequestBody Customer customer){
        LOGGER.info(String.format("Get request: /registration arrived. Username: %s",customer.getUsername() ));
        authService.registerCustomer(customer);
        LOGGER.info(String.format("Get request: /registration processed. Customer created: %s", customer.getUsername()));
    }
}
