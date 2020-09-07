package com.codecool.pizzabackend.entity;

import com.codecool.pizzabackend.controller.dto.OrderrDTO;
import com.codecool.pizzabackend.controller.dto.PizzaQuantityDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderrTest {
    private List<PizzaQuantityDTO> generatePizzaQuantityDtoList(){
        PizzaQuantityDTO pizzaDTO = PizzaQuantityDTO.builder()
                .id(1l)
                .name("Salami pizza")
                .description("tastes reeel goooood")
                .quantity(1)
                .build();

        PizzaQuantityDTO pizza2DTO = PizzaQuantityDTO.builder()
                .id(2l)
                .name("Meatlover pizza")
                .description("tastes good ")
                .quantity(3)
                .build();

        List<PizzaQuantityDTO> expectedPizzas = new ArrayList<>();
        expectedPizzas.add(pizzaDTO);
        expectedPizzas.add(pizza2DTO);
        return expectedPizzas;
    }

    @Test
    void testGenerateIncomingOrderDTO() {
        Pizza pizza = Pizza.builder()
                .id(1l)
                .name("Salami pizza")
                .description("tastes reeel goooood")
                .price(6000)
                .build();

        Pizza pizza2 = Pizza.builder()
                .id(2l)
                .name("Meatlover pizza")
                .description("tastes good ")
                .price(6000)
                .build();

        List<PizzaQuantityDTO> expectedPizzas = generatePizzaQuantityDtoList();

        Customer customer = Customer.builder()
                .username("customer")
                .name("Anna")
                .password("pass")
                .role("ROLE_CUSTOMER")
                .email("customer1@gmail.com")
                .phoneNumber("0036709443402")
                .build();

        Address address = Address.builder()
                .city("Budapest")
                .postalCode("1065")
                .street("Nagymező street")
                .streetNumber("44").build();

        Cook cook = Cook.builder()
                .username("cook")
                .name("Józsi")
                .password("pass")
                .role("ROLE_COOK")
                .phoneNumber("0036709443402")
                .email("cook1@gmail.com")
                .build();

        DeliveryGuy deliveryGuy = DeliveryGuy.builder()
                .username("deliveryGuy")
                .name("Pista")
                .password("pass")
                .role("ROLE_DELIVERYGUY")
                .phoneNumber("0036709443401")
                .email("deliveryGuy@gmail.com")
                .build();

        Orderr order = Orderr.builder()
                .id(1l)
                .orderedPizzas(new HashMap<Pizza, Integer>() {{
                    put(pizza, 1);
                    put(pizza2, 3);
                }})
                .customer(customer)
                .cook(cook)
                .deliveryGuy(deliveryGuy)
                .address(address)
                .orderStatus(OrderStatus.ORDERED)
                .build();

        OrderrDTO expectedOrderDTO = OrderrDTO.builder()
                .id(1l)
                .orderStatus(OrderStatus.ORDERED)
                .customer(customer)
                .cook(cook)
                .deliveryGuy(deliveryGuy)
                .address(address)
                .incomingOrderedPizzas(expectedPizzas)
                .build();

        assertEquals(expectedOrderDTO,order.generateIncomingOrderDTO());
    }
}