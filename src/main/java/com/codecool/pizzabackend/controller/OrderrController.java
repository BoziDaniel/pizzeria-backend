package com.codecool.pizzabackend.controller;

import com.codecool.pizzabackend.controller.dto.OrderrDTO;
import com.codecool.pizzabackend.entity.User;
import com.codecool.pizzabackend.security.JwtTokenServices;
import com.codecool.pizzabackend.service.OrderService;
import com.codecool.pizzabackend.service.OrederrNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderrController {

    private OrderService orderService;
    private JwtTokenServices jwtTokenServices;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderrController.class);

    @Autowired
    public OrderrController(OrderService orderService, JwtTokenServices jwtTokenServices) {
        this.orderService = orderService;
        this.jwtTokenServices = jwtTokenServices;
    }

    @GetMapping("/active")
    public List<OrderrDTO> getActiveOrdersForUser(HttpServletRequest request) {
        LOGGER.info("GET request /orders/active/ arrived");
        User user = jwtTokenServices.getUserFromRequest(request);
        List<OrderrDTO> activieOrderDTOs = orderService.listActiveOrdersForUser(user.getId());
        LOGGER.info(" Get request: /orders/active/ processed. Id was: " + user.getId() + " \n Return value will be: " + activieOrderDTOs.toString());
        return activieOrderDTOs;
    }

    @PostMapping("/add-new")
    public void createNewOrder(@RequestBody OrderrDTO orderrDTO, HttpServletRequest request) {
        LOGGER.info("post request: /orders/add-new arrived. payload: " + orderrDTO.toString());
        User user = jwtTokenServices.getUserFromRequest(request);
        orderService.persistIncomingOrder(user.getId(), orderrDTO);
        LOGGER.info("post request: /orders/add-new processed. Id was: " + user.getId());
    }
    @PutMapping("/set-order-ready/{orderId}")
    public void setOrderStatusFromInProgressToReady(@PathVariable("orderId") Long id, HttpServletRequest request) throws OrederrNotFoundException {
        LOGGER.info("put request: /orders/set-order-ready/"+ id + " arrived");
        User user = jwtTokenServices.getUserFromRequest(request);
        orderService.setOrderStatusToReady(id, user.getId());
        LOGGER.info("put request: /orders/set-order-ready/"+ id + " processed");
    }
    @PutMapping("/set-order-delivered/{orderId}")
    public void setOrderStatusFromInDeliveryToDelivered(@PathVariable("orderId") Long id, HttpServletRequest request) throws OrederrNotFoundException {
        LOGGER.info("put request: /orders/set-order-delivered/"+ id + " arrived");
        User user = jwtTokenServices.getUserFromRequest(request);
        orderService.setOrderStatusToDelivered(id, user.getId());
        LOGGER.info("put request: /orders/set-order-delivered/"+ id + " arrived");
    }

    @PutMapping("assignCook/{orderId}")
    public void assignCookToOrder(@PathVariable("orderId") Long id, @RequestBody Long cookId) throws OrederrNotFoundException {
        LOGGER.info(String.format("put request: /orders/assignCook/%s arrived. cookId is: %s", id, cookId));
        orderService.assignCookToOrder(id,cookId);
        LOGGER.info(String.format("put request: /orders/assignCook/%s processed. cookId is: %s", id, cookId));
    }

    @PutMapping("assignDeliveryGuy/{orderId}")
    public void assignDeliceryGuyToOrder(@PathVariable("orderId") Long id, @RequestBody Long deliveryGuyId) throws OrederrNotFoundException {
        LOGGER.info(String.format("put request: /orders/assignDeliveryguy/%s arrived. deliveryGuyId is: %s", id, deliveryGuyId));
        orderService.assignDeliveriGuyToOrder(id,deliveryGuyId);
        LOGGER.info(String.format("put request: /orders/assignDeliveryguy/%s processed. deliveryGuyId is: %s", id, deliveryGuyId));
    }
}
