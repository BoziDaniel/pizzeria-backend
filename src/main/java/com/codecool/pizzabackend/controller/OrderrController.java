package com.codecool.pizzabackend.controller;

import com.codecool.pizzabackend.controller.dto.OrderrDTO;
import com.codecool.pizzabackend.entity.Orderr;
import com.codecool.pizzabackend.repository.OrderrRepository;
import com.codecool.pizzabackend.repository.UserRepository;
import com.codecool.pizzabackend.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE)
//@CrossOrigin(origins = "http://localhost:3000")
public class OrderrController {

    @Autowired
    private OrderrRepository orderrRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderrController.class);


    @GetMapping("/active/{userId}")
    public List<OrderrDTO> getActiveOrdersForUser(@PathVariable("userId") Long userId) {
        LOGGER.info("get request: /orders/active/" + userId + " arrived");
        List<OrderrDTO> activieOrderDTOs = orderService.listActiveOrdersForUser(userId);
        LOGGER.info(" Get request: /orders/active/" + userId + " processed. \n Return value will be: " + activieOrderDTOs.toString());
        return activieOrderDTOs;
    }

    @PostMapping("/{userId}")
    public void createNewOrder(@PathVariable("userId") Long userId, @RequestBody OrderrDTO orderrDTO) {
        LOGGER.info("post request: /orders/" + userId + " arrived. payload: " + orderrDTO.toString());
        orderService.persistIncomingOrder(userId, orderrDTO);
        LOGGER.info("post request: /orders/" + userId + " processed.");
    }
}
