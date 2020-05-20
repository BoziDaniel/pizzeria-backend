package com.codecool.pizzabackend.repository;

import com.codecool.pizzabackend.entity.IncomingOrder;
import com.codecool.pizzabackend.entity.OrderStatus;
import com.codecool.pizzabackend.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomingOrderRepository extends JpaRepository<IncomingOrder, Long> {
    List<IncomingOrder> getIncomingOrdersByOrderStatusNotLikeAndCustomer_IdIs(OrderStatus orderStatus, Long id);
}
