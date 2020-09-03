package com.codecool.pizzabackend.service;

import com.codecool.pizzabackend.controller.dto.OrderrDTO;
import com.codecool.pizzabackend.controller.dto.PizzaQuantityDTO;
import com.codecool.pizzabackend.entity.*;
import com.codecool.pizzabackend.repository.AddressRepository;
import com.codecool.pizzabackend.repository.OrderrRepository;
import com.codecool.pizzabackend.repository.PizzaRepository;
import com.codecool.pizzabackend.repository.UserRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private OrderrRepository orderrRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    public void persistIncomingOrder(Long userId, OrderrDTO orderrDTO) {
        LOGGER.trace("Starting to create incoming order from userId: " + userId + "incomingOrderDTO: " + orderrDTO.toString());
        HashMap<Pizza, Integer> pizzas = new HashMap<>();
        for (PizzaQuantityDTO orderedPizzaQuantity : orderrDTO.getIncomingOrderedPizzas()) {
            pizzas.put(pizzaRepository.getPizzaById(orderedPizzaQuantity.getId()), orderedPizzaQuantity.getQuantity());
        }
        User customer = userRepository.getUserById(userId);
        Address address = orderrDTO.getAddress();
        addressRepository.save(address);
        Orderr orderr = Orderr.builder()
                .customer((Customer) customer)
                .orderStatus(OrderStatus.ORDERED)
                .orderedPizzas(pizzas)
                .address(address)
                .build();

        LOGGER.info("Incoming order created. incoming order: " + orderr.toString());
        orderrRepository.save(orderr);
        LOGGER.info("Incoming order persisted to db. incoming order: " + orderr.toString());
    }

    private List<OrderrDTO> generateIncomingOrderDTOsFromOrders(List<Orderr> orders) {
        List<OrderrDTO> orderDTOs = new ArrayList<>();
        for (Orderr order : orders) {
            orderDTOs.add(order.generateIncomingOrderDTO());
        }
        return orderDTOs;
    }

    public List<OrderrDTO> listActiveOrdersForUser(Long userId) {
        LOGGER.info("listActiveOrdersForUser started");
        //TODO: Maybe do the the whole thing in one sql.
        //TODO: error handling!
        User user = userRepository.getUserById(userId);
        String userRole = user.getRoles().stream().findFirst().get();
        LOGGER.info(" User role queired for userid: " + userId + " found role: " + userRole);
        List<Orderr> activeOrders = new ArrayList<>();
        switch (userRole) {
            case "ROLE_CUSTOMER": {
                LOGGER.info("Starting to list orders for customer. user id: " + userId);
                activeOrders = orderrRepository.getOrderrsByOrderStatusNotLikeAndCustomer_IdIs(OrderStatus.DELIVERED, userId);
                LOGGER.info(String.format("active orders are: %s",activeOrders.toString()));
                break;
            }
            case "ROLE_MANAGER": {
                LOGGER.info("Starting to list orders for manager. user id: " + userId);
                activeOrders = orderrRepository.getOrderrsByOrderStatusNotLike(OrderStatus.DELIVERED);
                break;
            }
            case "ROLE_COOK": {
                LOGGER.info("Starting to list orders for cook. user id: " + userId);
                activeOrders = orderrRepository.getCookActiveAssignedOrders(userId);
                break;
            }
            case "ROLE_DELIVERYGUY": {
                LOGGER.info("Starting to list orders for deliveryGuy. user id: " + userId);
                activeOrders = orderrRepository.getDeliveryGuyActiveAssignedOrders(userId);
                break;
            }
        }
        List<OrderrDTO> activeOrdersDTOs = generateIncomingOrderDTOsFromOrders(activeOrders);
        return activeOrdersDTOs;
    }

    private boolean isCookOwnsOrder(Long orderId, Long cookId){
        LOGGER.info(String.format("Started to check if order with id: %s is owned by cook with id: %s", orderId,cookId));
        boolean result = orderrRepository.isOrderOwnedByCook(orderId,cookId);
        LOGGER.info(String.format("Finished to check if order with id: %s is owned by cook with id: %s result is: %s", orderId,cookId,result));
        return result;
    }

    private boolean isDeliveryGuyOwnsOrder(Long orderId, Long deliveryGuyId){
        LOGGER.info(String.format("Started to check if order with id: %s is owned by delivery guy with id: %s", orderId,deliveryGuyId));
        boolean result = orderrRepository.isOrderOwnedByDeliveryGuy(orderId,deliveryGuyId);
        LOGGER.info(String.format("Finished to check if order with id: %s is owned by delivery guy with id: %s result is: %s", orderId,deliveryGuyId,result));
        return result;
    }

    public void setOrderStatusToReady(Long orderId, Long cookId) throws OrederrNotFoundException {
        LOGGER.info(String.format("Started the process of updating order status to READY order id: %s is  cook id: %s", orderId,cookId));
        if(isCookOwnsOrder(orderId, cookId)){
            Orderr orderr = orderrRepository.findById(orderId)
                    .orElseThrow(() -> new OrederrNotFoundException(String.format("Order with id: %s not found",orderId)));
            orderr.setOrderStatus(OrderStatus.READY);
            orderrRepository.save(orderr);
        }
        LOGGER.info(String.format("Finished the process of updating order status to READY order id: %s is  cook id: %s", orderId,cookId));
    }

    public void setOrderStatusToDelivered(Long orderId, Long deliveryguyId) throws OrederrNotFoundException {
        LOGGER.info(String.format("Started the process of updating order status to DELIVERED order id: %s is deliveryguy id: %s", orderId,deliveryguyId));
        if(isDeliveryGuyOwnsOrder(orderId, deliveryguyId)){
            Orderr orderr = orderrRepository.findById(orderId)
                    .orElseThrow(() -> new OrederrNotFoundException(String.format("Order with id: %s not found",orderId)));
            orderr.setOrderStatus(OrderStatus.DELIVERED);
            orderrRepository.save(orderr);
        }
        LOGGER.info(String.format("Finished the process of updating order status to DELIVERED order id: %s is  deliveryguy id: %s", orderId,deliveryguyId));
    }

    public void assignCookToOrder(Long orderId ,Long cookId) throws OrederrNotFoundException {
        LOGGER.info(String.format("Started the process of updating order with id %s with cook with id %s ", orderId, cookId));
        Orderr orderr = orderrRepository.findById(orderId)
                .orElseThrow(() -> new OrederrNotFoundException(String.format("Order with id: %s not found",orderId)));
        Cook cook = (Cook)userRepository.findById(cookId).orElseThrow(() -> new UsernameNotFoundException("User with id : " + cookId + " not found"));
        orderr.setCook(cook);
        orderrRepository.save(orderr);
        LOGGER.info(String.format("Finished the process of updating order with id %s with cook with id %s ", orderId, cookId));
    }

    public void assignDeliveriGuyToOrder(Long orderId ,Long deliveryGuyId) throws OrederrNotFoundException {
        LOGGER.info(String.format("Started the process of updating order with id %s with delivery guy with id %s ", orderId, deliveryGuyId));
        Orderr orderr = orderrRepository.findById(orderId)
                .orElseThrow(() -> new OrederrNotFoundException(String.format("Order with id: %s not found",orderId)));
        DeliveryGuy deliveryGuy = (DeliveryGuy) userRepository.findById(deliveryGuyId).orElseThrow(() -> new UsernameNotFoundException("User with id : " + deliveryGuyId + " not found"));
        orderr.setDeliveryGuy(deliveryGuy);
        orderrRepository.save(orderr);
        LOGGER.info(String.format("Finished the process of updating order with id %s with delivery guy with id %s ", orderId, deliveryGuyId));
    }


}




