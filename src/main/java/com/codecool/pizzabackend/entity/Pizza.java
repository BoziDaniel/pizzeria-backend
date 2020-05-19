package com.codecool.pizzabackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
