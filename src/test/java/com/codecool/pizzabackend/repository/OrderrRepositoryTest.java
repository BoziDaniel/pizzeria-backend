package com.codecool.pizzabackend.repository;

import com.codecool.pizzabackend.entity.Orderr;
import com.codecool.pizzabackend.entity.Pizza;
import com.codecool.pizzabackend.init.DbInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderrRepositoryTest {
    @Autowired
    DbInitializer dbInitializer;

    @Autowired
    OrderrRepository orderrRepository;

    @Autowired
    PizzaRepository pizzaRepository;

    @Test
    void test(){
        Pizza pizza = Pizza.builder()
                .name("Songoku")
                .description("Tasty")
                .price(47000)
                .build();
        pizzaRepository.save(pizza);
        Orderr orderr = Orderr.builder()
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 2);
                }})
                .build();
        orderrRepository.saveAndFlush(orderr);
        List<Orderr> orders = orderrRepository.findAll();
        System.out.println(orders);
    }

}