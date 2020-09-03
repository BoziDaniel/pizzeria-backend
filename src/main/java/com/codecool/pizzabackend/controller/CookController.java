package com.codecool.pizzabackend.controller;

import com.codecool.pizzabackend.controller.dto.UserDTO;
import com.codecool.pizzabackend.service.CookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "/cook")
public class CookController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderrController.class);

    @Autowired
    private CookService cookService;

    @GetMapping("/all")
    public List<UserDTO> getAllCooks() {
        LOGGER.info("Get request: /cook/all arrived");
        List<UserDTO> cooks = cookService.getAllCooks();
        LOGGER.info(" Get request: /cook/all processed. Return value will be: " + cooks.toString());
        return cooks;
    }
}
