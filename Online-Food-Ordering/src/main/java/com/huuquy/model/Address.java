package com.huuquy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Address {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    String streetAddress;
    String city;
    String stateProvice;
    String postalCode ;
    String country;
}
