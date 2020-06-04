package com.codecool.pizzabackend.controller;

import com.codecool.pizzabackend.controller.dto.OrderrDTO;
import com.codecool.pizzabackend.jwt.JwtTokenService;
import com.codecool.pizzabackend.repository.OrderrRepository;
import com.codecool.pizzabackend.repository.UserRepository;
import com.codecool.pizzabackend.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderrController {

    @Autowired
    private OrderrRepository orderrRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenService jwtTokenService;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderrController.class);

    //@PreAuthorize("hasAnyAuthority('customer:read', 'cook:read', 'manager:read', 'deliveryguy:read')")
    @PreAuthorize("hasAnyRole('ROLE_COOK', 'ROLE_CUSTOMER', 'ROLE_MANAGER', 'ROLE_DELIVERY_GUY')")
    @GetMapping("/active")
    public List<OrderrDTO> getActiveOrdersForUser(HttpServletRequest request) {
        LOGGER.info("get request: /orders/active/ arrived");
        LOGGER.info(request.getHeader("Authorization"));
        Long idFromToken = jwtTokenService.getIdFromRequestThroughToken(request);
        LOGGER.info(String.format("id from token: %s", idFromToken));
        List<OrderrDTO> activieOrderDTOs = orderService.listActiveOrdersForUser(idFromToken);
        LOGGER.info(" Get request: /orders/active/ processed. Id was: " + idFromToken +" \n Return value will be: " + activieOrderDTOs.toString());
        return activieOrderDTOs;
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/add-new")
    public void createNewOrder(@RequestBody OrderrDTO orderrDTO, HttpServletRequest request) {
        LOGGER.info("post request: /orders/add-new arrived. payload: " + orderrDTO.toString());
        Long idFromToken = jwtTokenService.getIdFromRequestThroughToken(request);
        LOGGER.info(String.format("id from token: %s", idFromToken));
        orderService.persistIncomingOrder(idFromToken, orderrDTO);
        LOGGER.info("post request: /orders/add-new processed. Id was: " + idFromToken);
    }


}
