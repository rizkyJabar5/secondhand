package com.secondhand.ecommerce.service.impl;

import com.secondhand.ecommerce.exceptions.NotFoundException;
import com.secondhand.ecommerce.models.entity.Categories;
import com.secondhand.ecommerce.repository.CategoriesRepository;
import com.secondhand.ecommerce.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepository categoryRepository;

    @Override
    public Categories addNewCategory(Categories category) {
        return categoryRepository.save(category);
    }

    @Override
    public Categories loadCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("No category with id " + categoryId));
    }

    @Override
    public Categories deleteCategoryById(Long categoryId) {
        categoryRepository.deleteById(categoryId);
        return null;
    }
}
