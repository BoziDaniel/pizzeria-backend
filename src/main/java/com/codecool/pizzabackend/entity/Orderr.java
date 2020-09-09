package com.codecool.pizzabackend.entity;

import com.codecool.pizzabackend.controller.dto.OrderrDTO;
import com.codecool.pizzabackend.controller.dto.PizzaQuantityDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Orderr {
    private static final Logger LOGGER = LoggerFactory.getLogger(Orderr.class);

    @GeneratedValue
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "incomingOrder_pizza_mapping",
            joinColumns = {@JoinColumn(name = "incomingOrder_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "pizza_name")
    @Column(name = "quantity")
    @Singular
    Map<Pizza, Integer> orderedPizzas;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JsonIgnore
    private Cook cook;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JsonIgnore
    private DeliveryGuy deliveryGuy;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JsonIgnore
    private Customer customer;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JsonIgnore
    private Address address;

    public OrderrDTO generateIncomingOrderDTO() {
        List<PizzaQuantityDTO> pizzaDTOs = generatePizzaQuantityDTOs();
        OrderrDTO orderrDTO = OrderrDTO.builder()
                .id(this.getId())
                .cook(this.cook)
                .deliveryGuy(this.deliveryGuy)
                .orderStatus(this.getOrderStatus())
                .customer(this.getCustomer())
                .incomingOrderedPizzas(pizzaDTOs)
                .address(this.address)
                .build();
        LOGGER.debug("incomingorderDTO: " + orderrDTO.toString());
        return orderrDTO;
    }

    private List<PizzaQuantityDTO> generatePizzaQuantityDTOs(){
        List<PizzaQuantityDTO> pizzaDTOs = new ArrayList<>();
        for (Pizza pizza : orderedPizzas.keySet()) {
            PizzaQuantityDTO pizzaDTO = generateDTOfromPizza(pizza);
            pizzaDTOs.add(pizzaDTO);
        }
        return pizzaDTOs;
    }

    private PizzaQuantityDTO generateDTOfromPizza(Pizza pizza) {
        PizzaQuantityDTO pizzaDTO = PizzaQuantityDTO.builder()
                .id(pizza.getId())
                .name(pizza.getName())
                .description(pizza.getDescription())
                .quantity(orderedPizzas.get(pizza))
                .build();
        return pizzaDTO;
    }
}
