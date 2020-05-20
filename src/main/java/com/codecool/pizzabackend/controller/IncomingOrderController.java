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


    // it seems to be fine for now but if we cant handle the bit wierd json we have to write a converter method here
    @GetMapping("/active/{userId}")
    public List<IncomingOrder> getActiveOrdersForUser(@PathVariable("userId") Long userId) {
        LOGGER.info("get request: /orders/active/" + userId + " arrived");
        List<IncomingOrder> activeOrders = incomingOrderRepository.getIncomingOrdersByOrderStatusNotLikeAndCustomer_IdIs(OrderStatus.DELIVERED, userId);
        LOGGER.info(" Get request: /orders/active/" + userId + " processed. \n Return value will be: " + activeOrders.toString());
        return activeOrders;
    }

    @PostMapping("/{userId}")
    public void createNewOrder(@PathVariable("userId") Long userId, @RequestBody IncomingOrderDTO incomingOrderDTO) {
        LOGGER.info("post request: /orders/" + userId + " arrived. payload: " + incomingOrderDTO.toString());
        orderService.persistIncomingOrder(userId, incomingOrderDTO);
        LOGGER.info("post request: /orders/" + userId + " processed.");
    }

}
