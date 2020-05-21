package com.codecool.pizzabackend.repository;

import com.codecool.pizzabackend.entity.Orderr;
import com.codecool.pizzabackend.entity.OrderStatus;
import com.codecool.pizzabackend.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderrRepository extends JpaRepository<Orderr, Long> {
    List<Orderr> getOrderrsByOrderStatusNotLikeAndCustomer_IdIs(Long id, OrderStatus orderStatus);
    List<Orderr> getOrderrsByOrderStatusNotLike(OrderStatus orderStatus);

    //TODO: ASK mentors how this black magic works
    @Query(value = "SELECT *\n" +
            "FROM ORDERR\n" +
            "    JOIN COOK_ASSIGNED_ORDERS ON ORDERR.ID = COOK_ASSIGNED_ORDERS.ASSIGNED_ORDERS_ID\n" +
      //      "    JOIN INCOMING_ORDER_PIZZA_MAPPING IOPM on ORDERR.ID = IOPM.INCOMING_ORDER_ID\n" +
      //      "    JOIN PIZZA P on IOPM.ORDERED_PIZZAS_KEY = P.ID\n" +
            "WHERE COOK_ID  = ?1 AND ORDER_STATUS = 'IN_PROGRESS';", nativeQuery = true)
    List<Orderr> getCookActiveAssignedOrders( Long id);

    @Query(value = "SELECT *\n" +
            "FROM ORDERR\n" +
            "    JOIN DELIVERY_GUY_ORDERS_TO_DELIVER DGOTD on ORDERR.ID = DGOTD.ASSIGNED_ORDERS_ID\n" +
//            "    JOIN INCOMING_ORDER_PIZZA_MAPPING IOPM on ORDERR.ID = IOPM.INCOMING_ORDER_ID\n" +
//            "    JOIN PIZZA on IOPM.ORDERED_PIZZAS_KEY = PIZZA.ID\n" +
            "WHERE DELIVERY_GUY_ID  = ?1 AND ORDER_STATUS = 'IN_DELIVERY';", nativeQuery = true)
    List<Orderr> getDeliveryGuyActiveAssignedOrders( Long id);
}
