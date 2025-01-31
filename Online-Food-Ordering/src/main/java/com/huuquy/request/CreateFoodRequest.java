package com.huuquy.request;

import com.huuquy.model.Category;
import com.huuquy.model.IngredientsItem;
import lombok.Data;

import java.util.List;
@Data
public class CreateFoodRequest {
    private String name;
    private String description;
    private Long price ;
    private Category category;
    private List<String> images;

    private Long restaurantId;
    private boolean vegatarin;
    private boolean seasinal;
    private List<IngredientsItem> ingredients;

}
