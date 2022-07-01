package com.secondhand.ecommerce.service;

import com.secondhand.ecommerce.models.entity.Categories;
import com.secondhand.ecommerce.utils.HasLogger;

public interface CategoriesService extends HasLogger {

    Categories addNewCategory(Categories category);

    Categories loadCategoryById(Long categoryId);

    Categories deleteCategoryById(Long categoryId);
}
