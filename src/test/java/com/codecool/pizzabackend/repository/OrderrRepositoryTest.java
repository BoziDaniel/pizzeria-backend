package com.codecool.pizzabackend.repository;

import com.codecool.pizzabackend.entity.*;
import com.codecool.pizzabackend.init.DbInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderrRepositoryTest {
    @Autowired
    DbInitializer dbInitializer;

    @Autowired
    OrderrRepository orderrRepository;

    @Autowired
    PizzaRepository pizzaRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

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
        orderrRepository.save(orderr);
        List<Orderr> orders = orderrRepository.findAll();
    }

    @Test
    void testGetOrderrsByOrderStatusNotLikeAndCustomer_IdIs(){
        Pizza pizza = Pizza.builder()
                .name("Songoku")
                .description("Tasty")
                .price(47000)
                .build();
        pizzaRepository.save(pizza);
        Customer customer = Customer.builder()
                .username("customer")
                .name("Anna")
                .password("pass")
                .role("ROLE_CUSTOMER")
                .email("customer1@gmail.com")
                .phoneNumber("0036709443402")
                .build();
        userRepository.save(customer);


        Customer customer1 = Customer.builder()
                .username("customer1")
                .name("Péter")
                .password("pass")
                .role("ROLE_CUSTOMER")
                .phoneNumber("0036709443402")
                .email("customer2@gmail.com")
                .build();
        userRepository.save(customer1);
        Orderr orderr = Orderr.builder()
                .customer(customer)
                .orderStatus(OrderStatus.IN_PROGRESS)
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 12);
                }})
                .build();
        orderrRepository.save(orderr);
        Orderr orderr1 = Orderr.builder()
                .customer(customer1)
                .orderStatus(OrderStatus.IN_PROGRESS)
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 2);
                }})
                .build();
        orderrRepository.save(orderr1);

        Orderr orderr2 = Orderr.builder()
                .customer(customer)
                .orderStatus(OrderStatus.DELIVERED)
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 2);
                }})
                .build();
        orderrRepository.save(orderr2);

        List<Orderr> orderrs =orderrRepository.getOrderrsByOrderStatusNotLikeAndCustomer_IdIs(OrderStatus.DELIVERED,customer.getId());
        assertThat(orderrs).hasSize(1);
        assertEquals(orderr,orderrs.get(0));
    }
}