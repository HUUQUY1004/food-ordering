package com.huuquy.request;

import com.huuquy.model.Address;
import com.huuquy.model.ContactInformation;
import lombok.Data;

import java.util.List;

@Data
public class CreateRestaurantRequest {
    private Long id ;
    private String name;
    private String description;
    private String cuisineType ;
    private Address address;
    private ContactInformation contactInformation;
    private String opningHours;
    private List<String> images;
}
