package com.codecool.pizzabackend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class IncomingOrder {
    @GeneratedValue
    @Id
    private Long id;

    @ElementCollection
    @CollectionTable(name = "incomingOrder_pizza_mapping",
            joinColumns = {@JoinColumn(name = "incomingOrder_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "pizza_name")
    @Column(name = "quantity")
    @Singular

    Map<Pizza, Integer> orderedPizzas;
}
