package com.codecool.pizzabackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Address {

    @GeneratedValue
    @Id
    private Long id;
    private String city;
    private String postalCode;
    private String street;
    private String streetNumber;
    private String comment;
    @OneToMany(mappedBy = "address")
    @JsonIgnore
    private Set<Orderr> ordersToAddress;

    public Address(String city, String postalCode, String street, String streetNumber, String comment) {
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.streetNumber = streetNumber;
        this.comment = comment;

    }
}
