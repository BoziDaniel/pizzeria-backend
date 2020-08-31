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
@RequestMapping(value = "/deliveryGuy")
public class DeliveryGuyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderrController.class);
    @Autowired
    private UserRepository userRepository;

    public DeliveryGuyController(UserRepository userRepository) {
        this.userRepository=userRepository;
    }

    @GetMapping("/all")
    public List<UserDTO> getAllDeliveryGuys() {
        LOGGER.info("Get request: /deliveryGuy/all arrived");
        //Todo: use sevice layer at least, but best to use db query instead filter
        List<UserDTO> deliveryGuys = userRepository.findAll().stream()
                .filter(user -> user.getRoles().contains("ROLE_DELIVERYGUY"))
                .map(deliveryGuy -> deliveryGuy.createDTO())
                .collect(Collectors.toList());
        LOGGER.info(" Get request: /deliveryGuy/all processed. Return value will be: " + deliveryGuys.toString());
        return deliveryGuys;
    }
}
