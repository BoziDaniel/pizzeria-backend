package com.codecool.pizzabackend.controller;

import com.codecool.pizzabackend.entity.User;
import com.codecool.pizzabackend.repository.UserRepository;
import com.codecool.pizzabackend.service.OrederrNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/exists/{username}")
    public boolean isUserExistByName(@PathVariable("username") String username){
        LOGGER.info(String.format("Get request arrived to path: /users/exists/%s",username));
        boolean isUserWithNameExist = userRepository.existsByUsername(username);
        LOGGER.info(String.format("Get request /users/exists/%s processed. result: %s",username, isUserWithNameExist));
        return isUserWithNameExist;
    }
    @GetMapping("/exists")
    public boolean isPhoneNumberOccupied(@RequestParam("phoneNumber") String phoneNumber){
        LOGGER.info(String.format("Get request arrived to path: /users/exists?phoneNumber=%s", phoneNumber));
        boolean isPhoneNumberAlreadyInUse = userRepository.existsByPhoneNumber(phoneNumber);
        LOGGER.info(String.format("Get request /users/exists/%s processed. result: %s",phoneNumber, isPhoneNumberAlreadyInUse));
        return isPhoneNumberAlreadyInUse;
    }
    @GetMapping("/email/exists")
    public boolean isEmailOccupied(@RequestParam("email") String email){
        LOGGER.info(String.format("Get request arrived to path: /users/email/exists?email=%s", email));
        boolean isEmailAlreadyInUse = userRepository.existsByEmail(email);
        LOGGER.info(String.format("Get request /users/email/exists?%s processed. result: %s",email, isEmailAlreadyInUse));
        return isEmailAlreadyInUse;
    }
}
