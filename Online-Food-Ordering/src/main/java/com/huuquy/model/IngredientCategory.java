package com.huuquy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class IngredientCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public  Long id;

    private  String name;

    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<IngredientsItem> ingredientsItems = new ArrayList<>();
}
