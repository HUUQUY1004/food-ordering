package com.huuquy.controller;

import com.huuquy.model.Category;
import com.huuquy.model.User;
import com.huuquy.service.CategoryService;
import com.huuquy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;
    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(
            @RequestHeader("Authorization") String jwt,
            @RequestBody Category category
    ) throws  Exception{
        User user = userService.findUserByJwtToken(jwt);
        Category createCategory = categoryService.createCategoty(category.getName(), user.getId());
        return  new ResponseEntity<>(createCategory, HttpStatus.CREATED);
    }
    @GetMapping("/restaurant")
    public ResponseEntity<List<Category>> getRestaurantCategory(
            @RequestHeader("Authorization") String jwt
    ) throws  Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Category> categories= categoryService.findCategoryByRestaurantId(user.getId());
        return  new ResponseEntity<>(categories, HttpStatus.OK);
    }

}
