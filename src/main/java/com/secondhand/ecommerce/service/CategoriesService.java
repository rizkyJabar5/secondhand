package com.secondhand.ecommerce.service;

import com.secondhand.ecommerce.models.entity.Categories;
import com.secondhand.ecommerce.utils.HasLogger;

import java.util.List;

public interface CategoriesService extends HasLogger {

    Categories addNewCategory(Categories category);

    Categories loadCategoryById(Long categoryId);

    List<Categories> findAllCategories();

    Categories deleteCategoryById(Long categoryId);
}
