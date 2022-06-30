package com.secondhand.ecommerce.service;

import com.secondhand.ecommerce.exceptions.NotFoundException;
import com.secondhand.ecommerce.models.entity.Categories;
import com.secondhand.ecommerce.repository.CategoriesRepository;
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
    public void deleteCategoryById(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
