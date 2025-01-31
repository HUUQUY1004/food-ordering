package com.huuquy.service;

import com.huuquy.model.IngredientCategory;
import com.huuquy.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {
    IngredientCategory createIngredientCategory(String name, Long restuarantId) throws  Exception;

    IngredientCategory findIngredientCategoryById(Long id) throws Exception;

    List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws  Exception;

    List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) throws  Exception;

    IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId) throws  Exception;
    IngredientsItem updateStock(Long id) throws  Exception;
}
