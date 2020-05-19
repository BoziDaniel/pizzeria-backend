package com.codecool.pizzabackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Customer")
@Data
@AllArgsConstructor
@NoArgsConstructor

@SuperBuilder

public class Customer extends User {

}
