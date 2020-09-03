package com.codecool.pizzabackend.controller;

import com.codecool.pizzabackend.controller.dto.UserDTO;
import com.codecool.pizzabackend.service.DeliveryGuyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "/deliveryGuy")
public class DeliveryGuyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderrController.class);
    private DeliveryGuyService deliveryGuyService;

    @Autowired
    public DeliveryGuyController(DeliveryGuyService deliveryGuyService) {
        this.deliveryGuyService = deliveryGuyService;
    }

    @GetMapping("/all")
    public List<UserDTO> getAllDeliveryGuys() {
        LOGGER.info("Get request: /deliveryGuy/all arrived");
        List<UserDTO> deliveryGuys = deliveryGuyService.getAllDeliveryGuys();
        LOGGER.info(" Get request: /deliveryGuy/all processed. Return value will be: " + deliveryGuys.toString());
        return deliveryGuys;
    }
}
