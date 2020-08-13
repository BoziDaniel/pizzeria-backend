package com.codecool.pizzabackend.controller;

import com.codecool.pizzabackend.controller.dto.UserDTO;
import com.codecool.pizzabackend.entity.Cook;
import com.codecool.pizzabackend.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.codecool.pizzabackend.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/cook")
public class CookController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderrController.class);
    @Autowired
    private UserRepository userRepository;

    public CookController(UserRepository userRepository) {
        this.userRepository=userRepository;
    }

    @GetMapping("/all")
    public List<UserDTO> getActiveOrdersForUser() {
        LOGGER.info("Get request: /cook/all arrived");
        //Todo: use sevice layer at least, but best to use db query instead filter
        List<UserDTO> cooks = userRepository.findAll().stream()
                .filter(user -> user.getRoles().contains("ROLE_COOK"))
                .map(cook -> cook.createDTO())
                .collect(Collectors.toList());
        LOGGER.info(" Get request: /cook/all processed. Return value will be: " + cooks.toString());
        return cooks;
    }
}
