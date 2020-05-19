package com.codecool.pizzabackend.repository;

import com.codecool.pizzabackend.entity.IncomingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomingOrderRepository extends JpaRepository<IncomingOrder, Long> {
}
