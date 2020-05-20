package com.codecool.pizzabackend.controller;

import com.codecool.pizzabackend.entity.IncomingOrder;
import com.codecool.pizzabackend.entity.OrderStatus;
import com.codecool.pizzabackend.repository.IncomingOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
//@CrossOrigin(origins = "http://localhost:3000")
public class IncomingOrderController {

    @Autowired
    private IncomingOrderRepository incomingOrderRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(IncomingOrderController.class);


    // it seems to be fine for now but if we cant handle the bit wierd json we have to write a converter method here
@GetMapping("/active/{userId}")
public List<IncomingOrder> getActiveOrdersForUser(@PathVariable("userId") Long userId) {
    LOGGER.info("get request: /orders/active/" + userId + " arrived");
    List<IncomingOrder> activeOrders = incomingOrderRepository.getIncomingOrdersByOrderStatusNotLikeAndCustomer_IdIs(OrderStatus.DELIVERED, userId);
    LOGGER.info(" Get request: /orders/active/" + userId +" processed. \n Return value will be: " + activeOrders.toString());
    return activeOrders;
}


}
