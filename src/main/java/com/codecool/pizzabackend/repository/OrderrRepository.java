package com.codecool.pizzabackend.repository;

import com.codecool.pizzabackend.entity.OrderStatus;
import com.codecool.pizzabackend.entity.Orderr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderrRepository extends JpaRepository<Orderr, Long> {
    List<Orderr> getOrderrsByOrderStatusNotLikeAndCustomer_IdIs(OrderStatus orderStatus, Long id);

    List<Orderr> getOrderrsByOrderStatusNotLike(OrderStatus orderStatus);

    //TODO: ASK mentors how this black magic works
    @Query(value = "SELECT *\n" +
            "FROM ORDERR\n" +
            "    JOIN COOK_ASSIGNED_ORDERS ON ORDERR.ID = COOK_ASSIGNED_ORDERS.ASSIGNED_ORDERS_ID\n" +
            "WHERE COOK_ID  = ?1 AND ORDER_STATUS = 'IN_PROGRESS';", nativeQuery = true)
    List<Orderr> getCookActiveAssignedOrders(Long id);

    @Query(value = "SELECT *\n" +
            "FROM ORDERR\n" +
            "    JOIN DELIVERY_GUY_ORDERS_TO_DELIVER DGOTD on ORDERR.ID = DGOTD.ASSIGNED_ORDERS_ID\n" +
            "WHERE DELIVERY_GUY_ID  = ?1 AND ORDER_STATUS = 'IN_DELIVERY';", nativeQuery = true)
    List<Orderr> getDeliveryGuyActiveAssignedOrders(Long id);
}
