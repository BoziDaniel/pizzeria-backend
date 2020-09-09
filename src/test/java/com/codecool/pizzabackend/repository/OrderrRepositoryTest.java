package com.codecool.pizzabackend.repository;

import com.codecool.pizzabackend.entity.*;
import com.codecool.pizzabackend.init.DbInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void testGetOrderrsByOrderStatusNotLikeAndCustomer_IdIs() {
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

        List<Orderr> orderrs = orderrRepository.getOrderrsByOrderStatusNotLikeAndCustomer_IdIs(OrderStatus.DELIVERED, customer.getId());
        assertThat(orderrs).hasSize(1);
        assertEquals(orderr, orderrs.get(0));
    }

    @Test
    void testGetCookActiveAssignedOrders() {
        Pizza pizza = Pizza.builder()
                .name("Songoku")
                .description("Tasty")
                .price(47000)
                .build();
        pizzaRepository.save(pizza);

        Orderr orderr = Orderr.builder()
                .orderStatus(OrderStatus.IN_PROGRESS)
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 12);
                }})
                .build();
        orderrRepository.save(orderr);
        Orderr orderr1 = Orderr.builder()
                .orderStatus(OrderStatus.IN_PROGRESS)
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 2);
                }})
                .build();
        orderrRepository.save(orderr1);

        Orderr orderr2 = Orderr.builder()
                .orderStatus(OrderStatus.DELIVERED)
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 2);
                }})
                .build();
        orderrRepository.save(orderr2);

        Cook cook = Cook.builder()
                .username("cook")
                .name("Józsi")
                .password("pass")
                .role("ROLE_COOK")
                .assignedOrder(orderr)
                .assignedOrder(orderr2)
                .phoneNumber("0036709443402")
                .email("cook1@gmail.com")
                .build();
        userRepository.save(cook);
        orderr.setCook(cook);
        orderr2.setCook(cook);

        Cook cook2 = Cook.builder()
                .username("cook2")
                .name("Laci")
                .password("pass")
                .role("ROLE_COOK")
                .assignedOrder(orderr1)
                .phoneNumber("0036709443401")
                .email("cook2@gmail.com")
                .build();
        userRepository.save(cook2);
        orderr1.setCook(cook2);

        orderrRepository.save(orderr);
        orderrRepository.save(orderr1);
        orderrRepository.save(orderr2);

        List<Orderr> orderrs = orderrRepository.getCookActiveAssignedOrders(cook.getId());
        assertThat(orderrs).hasSize(1);
        assertEquals(orderr, orderrs.get(0));
    }

    @Test
    void testGetDeliveryGuyActiveAssignedOrders() {
        Pizza pizza = Pizza.builder()
                .name("Songoku")
                .description("Tasty")
                .price(47000)
                .build();
        pizzaRepository.save(pizza);

        Orderr orderr1 = Orderr.builder()
                .orderStatus(OrderStatus.IN_DELIVERY)
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 12);
                }})
                .build();
        orderrRepository.save(orderr1);
        Orderr orderr2 = Orderr.builder()
                .orderStatus(OrderStatus.IN_DELIVERY)
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 2);
                }})
                .build();
        orderrRepository.save(orderr2);

        Orderr orderr3 = Orderr.builder()
                .orderStatus(OrderStatus.DELIVERED)
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 2);
                }})
                .build();
        orderrRepository.save(orderr3);
        DeliveryGuy deliveryGuy1 = DeliveryGuy.builder()
                .username("deliveryGuy1")
                .name("Pista")
                .password("pass")
                .role("ROLE_DELIVERYGUY")
                .phoneNumber("0036709443401")
                .email("deliveryGuy1@gmail.com")
                .assignedOrder(orderr1)
                .assignedOrder(orderr3)
                .build();
        userRepository.save(deliveryGuy1);
        DeliveryGuy deliveryGuy2 = DeliveryGuy.builder()
                .username("deliveryGuy2")
                .name("Pistaa")
                .password("pass")
                .role("ROLE_DELIVERYGUY")
                .phoneNumber("00367094434011")
                .email("deliveryGuy2@gmail.com")
                .assignedOrder(orderr2)
                .build();
        userRepository.save(deliveryGuy2);
        orderr1.setDeliveryGuy(deliveryGuy1);
        orderr3.setDeliveryGuy(deliveryGuy1);
        orderr2.setDeliveryGuy(deliveryGuy2);
        orderrRepository.save(orderr1);
        orderrRepository.save(orderr2);
        orderrRepository.save(orderr3);
        List<Orderr> orderrs = orderrRepository.getDeliveryGuyActiveAssignedOrders(deliveryGuy1.getId());
        assertThat(orderrs).hasSize(1);
        assertEquals(orderr1, orderrs.get(0));
    }
}