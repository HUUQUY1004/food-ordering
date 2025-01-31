package com.huuquy.service;

import com.huuquy.model.Category;
import com.huuquy.model.Food;
import com.huuquy.model.Restaurant;
import com.huuquy.repository.FoodRepository;
import com.huuquy.request.CreateFoodRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class FoodServiceImp implements  FoodService{

    private FoodRepository foodRepository;
    @Override
    public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant) {
        Food food = new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(request.getDescription());
        food.setImages(request.getImages());
        food.setName(restaurant.getName());
        food.setPrice(request.getPrice());
        food.setIngredients(request.getIngredients());
        food.setSeasonal(request.isSeasinal());
        food.setVegetarian(request.isVegatarin());
        Food saveFood = foodRepository.save(food);
        restaurant.getFoods().add(saveFood);
        return saveFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);

    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegitarain, boolean isNoveg, boolean isSeasonal, String foodCategory) {
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);
        if(isVegitarain){
            foods = filterByVegetarianAndNonVeg(foods,isVegitarain);
        }
        if(isNoveg) {
            foods = filterByVegetarianAndNonVeg(foods,isVegitarain);
        }
        if(isSeasonal){
            foods = filterBySeasonal(foods,isSeasonal);
        }
        if(foodCategory !=null && !foodCategory.equals("")){
            foods = filterByCategory(foods,foodCategory);
        }
        return foods;
    }

    @Override
    public List<Food> searchFood(String key) {
        return foodRepository.searchFood(key);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood = foodRepository.findById(foodId);
        if(optionalFood.isEmpty()){
            throw  new Exception("Not found food with id: "+ foodId);
        }
        return  optionalFood.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
       Food food = findFoodById(foodId);
       food.setAvailable(!food.isAvailable());
       return  foodRepository.save(food);
    }

    @Override
    public List<Food> filterByVegetarianAndNonVeg(List<Food> foods, boolean isVegitarain) {
            return foods.stream().filter(food -> food.isVegetarian() == isVegitarain ).collect(Collectors.toList());
    }

    @Override
    public List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isVegetarian() == isSeasonal ).collect(Collectors.toList());
    }

    @Override
    public List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if(food.getFoodCategory() !=null){
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return  false;
        }).collect(Collectors.toList());
    }

}
