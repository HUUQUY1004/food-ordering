package com.huuquy.service;

import com.huuquy.model.Category;
import com.huuquy.model.Food;
import com.huuquy.model.Restaurant;
import com.huuquy.request.CreateFoodRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface FoodService {
     Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant);
     void deleteFood(Long foodId) throws  Exception;

     List<Food> getRestaurantsFood(Long restaurantId, boolean isVegitarain, boolean isNoveg, boolean isSeasonal, String foodCategory);

     List<Food> searchFood(String key);

     Food findFoodById(Long foodId) throws  Exception;

     Food updateAvailabilityStatus(Long foodId) throws  Exception;

     List<Food> filterByVegetarianAndNonVeg(List<Food> foods, boolean isVegitarain);

     List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal);

     List<Food> filterByCategory(List<Food> foods, String foodCategory);

}
