package com.codecool.pizzabackend.repository;

import com.codecool.pizzabackend.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository  extends JpaRepository<Address, Long> {
}
