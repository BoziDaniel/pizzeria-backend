package com.codecool.pizzabackend.repository;

import com.codecool.pizzabackend.init.DbInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderrRepositoryTest {
    @Autowired
    DbInitializer dbInitializer;

    @Test
    void test(){
        System.out.println(dbInitializer);
    }

}