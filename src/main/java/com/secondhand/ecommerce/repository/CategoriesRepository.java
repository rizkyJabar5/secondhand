package com.secondhand.ecommerce.repository;

import com.secondhand.ecommerce.models.entity.Categories;
import com.secondhand.ecommerce.models.enums.CategoryList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    Optional<Categories> findByName(CategoryList categoryList);

}
