package com.secondhand.ecommerce.service;

import com.secondhand.ecommerce.models.entity.Categories;
import com.secondhand.ecommerce.utils.HasLogger;

public interface CategoriesService extends HasLogger {

    void addNewCategory();

    Categories loadCategoryById(Long categoryId);

}
