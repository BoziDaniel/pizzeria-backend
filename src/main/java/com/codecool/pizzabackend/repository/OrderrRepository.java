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
    @Query(value = "SELECT * FROM ORDERR\n" +
            "WHERE COOK_ID  = ?1 AND ORDER_STATUS = 'IN_PROGRESS'", nativeQuery = true)
    List<Orderr> getCookActiveAssignedOrders(Long id);

    @Query(value = "SELECT * FROM ORDERR\n" +
            "WHERE DELIVERY_GUY_ID  = ?1 AND ORDER_STATUS = 'IN_DELIVERY'", nativeQuery = true)
    List<Orderr> getDeliveryGuyActiveAssignedOrders(Long id);

    @Query(value = "SELECT\n" +
            "    CASE WHEN EXISTS\n" +
            "        (SELECT  *\n" +
            "         FROM ORDERR\n" +
            "         WHERE ID=?1 and COOK_ID=?2)\n" +
            "             THEN TRUE\n" +
            "         ELSE FALSE\n" +
            "        END", nativeQuery = true)
    Boolean isOrderOwnedByCook(Long id, Long cookId);



}
