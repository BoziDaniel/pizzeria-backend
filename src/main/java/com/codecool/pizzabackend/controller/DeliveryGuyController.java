package com.codecool.pizzabackend.controller;

import com.codecool.pizzabackend.controller.dto.UserDTO;
import com.codecool.pizzabackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/deliveryguy")
public class DeliveryGuyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderrController.class);
    @Autowired
    private UserRepository userRepository;

    public DeliveryGuyController(UserRepository userRepository) {
        this.userRepository=userRepository;
    }

    @GetMapping("/all")
    public List<UserDTO> getAllDeliveryGuys() {
        LOGGER.info("Get request: /deliveryguy/all arrived");
        //Todo: use sevice layer at least, but best to use db query instead filter
        List<UserDTO> deliveryguys = userRepository.findAll().stream()
                .filter(user -> user.getRoles().contains("ROLE_DELIVERYGUY"))
                .map(deliveryguy -> deliveryguy.createDTO())
                .collect(Collectors.toList());
        LOGGER.info(" Get request: /deliveryguy/all processed. Return value will be: " + deliveryguys.toString());
        return deliveryguys;
    }
}
