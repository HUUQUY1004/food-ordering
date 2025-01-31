package com.huuquy.controller;

import com.huuquy.model.IngredientCategory;
import com.huuquy.model.IngredientsItem;
import com.huuquy.request.IngredientCategoryRequest;
import com.huuquy.request.IngredientRequest;
import com.huuquy.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredient")
public class IngredientController {

    @Autowired
    private IngredientsService ingredientsService;


    @PostMapping("/create/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest request
            ) throws  Exception {
        IngredientCategory ingredientCategory = ingredientsService.createIngredientCategory(request.getName(),request.getRestaurantId());
        return  new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
    }
    @PostMapping("/create/ingredient-item")
    public ResponseEntity<IngredientsItem> createIngredientItem(
            @RequestBody IngredientRequest request
    ) throws  Exception {
        IngredientsItem ingredientsItem = ingredientsService.createIngredientsItem(request.getRestaurantId(), request.getName() ,request.getCategoryId());
        return  new ResponseEntity<>(ingredientsItem, HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}/stock")
    public ResponseEntity<IngredientsItem> updateIngredientItemStock(
            @PathVariable Long id
    ) throws  Exception {
        IngredientsItem ingredientsItem = ingredientsService.updateStock(id);
        return  new ResponseEntity<>(ingredientsItem, HttpStatus.OK);
    }
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getrestaurantIngredient(
            @PathVariable Long id
    ) throws  Exception {
        List<IngredientsItem> ingredientsItem = ingredientsService.findRestaurantsIngredients(id);
        return  new ResponseEntity<>(ingredientsItem, HttpStatus.OK);
    }
    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getrestaurantIngredientCategory(
            @PathVariable Long id
    ) throws  Exception {
        List<IngredientCategory> ingredientsItem = ingredientsService.findIngredientCategoryByRestaurantId(id);
        return  new ResponseEntity<>(ingredientsItem, HttpStatus.OK);
    }
}
