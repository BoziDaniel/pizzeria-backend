package com.codecool.pizzabackend.controller;

import com.codecool.pizzabackend.controller.dto.IncomingOrderDTO;
import com.codecool.pizzabackend.controller.dto.PizzaQuantityDTO;
import com.codecool.pizzabackend.entity.IncomingOrder;
import com.codecool.pizzabackend.entity.OrderStatus;
import com.codecool.pizzabackend.repository.IncomingOrderRepository;
import com.codecool.pizzabackend.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE)
//@CrossOrigin(origins = "http://localhost:3000")
public class IncomingOrderController {

    @Autowired
    private IncomingOrderRepository incomingOrderRepository;
    @Autowired
    private OrderService orderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(IncomingOrderController.class);


    @GetMapping("/active/{userId}")
    public List<IncomingOrderDTO> getActiveOrdersForUser(@PathVariable("userId") Long userId) {
        LOGGER.info("get request: /orders/active/" + userId + " arrived");
        List<IncomingOrderDTO> activieOrderDTOs = orderService.listActiveOrdersForUser(userId);
        LOGGER.info(" Get request: /orders/active/" + userId + " processed. \n Return value will be: " + activieOrderDTOs.toString());
        return activieOrderDTOs;
    }

    @GetMapping("/active/all")
    public List<IncomingOrderDTO> getActiveOrdersForUser() {
        LOGGER.info("get request: /orders/active/all arrived");
        List<IncomingOrderDTO> activieOrderDTOs = orderService.listActiveOrdersForUser(userId);
        LOGGER.info(" Get request: /orders/active/all processed. \n Return value will be: " + activieOrderDTOs.toString());
        return activieOrderDTOs;
    }

    @PostMapping("/{userId}")
    public void createNewOrder(@PathVariable("userId") Long userId, @RequestBody IncomingOrderDTO incomingOrderDTO) {
        LOGGER.info("post request: /orders/" + userId + " arrived. payload: " + incomingOrderDTO.toString());
        orderService.persistIncomingOrder(userId, incomingOrderDTO);
        LOGGER.info("post request: /orders/" + userId + " processed.");
    }

}
