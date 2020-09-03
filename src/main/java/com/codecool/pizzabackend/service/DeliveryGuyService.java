package com.codecool.pizzabackend.service;

import com.codecool.pizzabackend.controller.dto.UserDTO;
import com.codecool.pizzabackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryGuyService {
    private UserRepository userRepository;

    @Autowired
    public DeliveryGuyService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Todo: best to use db query instead filter
    public List<UserDTO> getAllDeliveryGuys(){
        List<UserDTO> deliveryGuys = userRepository.findAll().stream()
                .filter(user -> user.getRoles().contains("ROLE_DELIVERYGUY"))
                .map(deliveryGuy -> deliveryGuy.createDTO())
                .collect(Collectors.toList());
        return deliveryGuys;
    }
}
