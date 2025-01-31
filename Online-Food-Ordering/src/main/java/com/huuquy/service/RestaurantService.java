package com.huuquy.service;

import com.huuquy.dto.RestaurantDto;
import com.huuquy.model.Restaurant;
import com.huuquy.model.User;
import com.huuquy.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {
    Restaurant createRestaurant(CreateRestaurantRequest request, User user) throws  Exception;
    Restaurant updateRestaurant(Long id, CreateRestaurantRequest updateRestaurant) throws  Exception;

    void delateRestaurant(Long id) throws  Exception;
    List<Restaurant> getAllRestaurant();

    List<Restaurant> searchRestaurant(String key);

    Restaurant findRestaurantById(Long id) throws  Exception;

    Restaurant getRestaurantByUserId(Long userId) throws  Exception;

    RestaurantDto addToFavorites(Long restaurantId, User user) throws  Exception;

    Restaurant updateRestaurantStatus(Long id) throws  Exception;

}
