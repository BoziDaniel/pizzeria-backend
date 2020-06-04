package com.codecool.pizzabackend.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Pizza {
    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;


    private int price;
    public Pizza(Long id) {
        this.id = id;
    }


}
