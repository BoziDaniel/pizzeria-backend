package com.codecool.pizzabackend.service;

import com.codecool.pizzabackend.controller.dto.UserDTO;
import com.codecool.pizzabackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CookService {
    private UserRepository userRepository;

    @Autowired
    public CookService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Todo: best to use db query instead filter
    public List<UserDTO> getAllCooks(){
        List<UserDTO> cooks = userRepository.findAll().stream()
                .filter(user -> user.getRoles().contains("ROLE_COOK"))
                .map(cook -> cook.createDTO())
                .collect(Collectors.toList());
        return cooks;
    }
}
