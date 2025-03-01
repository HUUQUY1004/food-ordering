package com.huuquy.service;

import com.huuquy.model.Category;

import java.util.List;

public interface CategoryService {
    Category createCategoty(String name, Long userId) throws Exception;

    List<Category> findCategoryByRestaurantId(Long id) throws  Exception;

    Category findCategoryById(Long id) throws  Exception;
}
