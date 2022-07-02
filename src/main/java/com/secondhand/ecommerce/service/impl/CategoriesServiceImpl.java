package com.secondhand.ecommerce.service.impl;

import com.secondhand.ecommerce.exceptions.AppBaseException;
import com.secondhand.ecommerce.models.entity.Categories;
import com.secondhand.ecommerce.models.enums.CategoryList;
import com.secondhand.ecommerce.repository.CategoriesRepository;
import com.secondhand.ecommerce.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepository categoryRepository;


    @Bean
    @Override
    public void addNewCategory() {
        for (CategoryList categoryList : CategoryList.values()) {
            try {
                Categories category = categoryRepository.findByName(categoryList)
                        .orElseThrow(() -> new RuntimeException(
                                String.format("Category: %s not found", categoryList)));

                getLogger().info("{} is found", category);
            } catch (RuntimeException e) {
                getLogger().info(String.format("Category: %s not found." + " It will be create ...", categoryList.name()));

                Categories category = new Categories();
                category.setName(categoryList.getName());
                categoryRepository.save(category);
            }
        }
    }

    @Override
    public Categories loadCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppBaseException("No category with id " + categoryId));
    }

}
