package com.secondhand.ecommerce.repository;

import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.models.entity.Categories;
import com.secondhand.ecommerce.models.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByProductNameIgnoreCaseAndCategoryIgnoreCase(String productName, Categories categoryId, Pageable pageable);

    Page<Product> findByCategoryIdContaining(Categories categoryId, Pageable pageable);

    Page<Product> findByProductNameContaining(String productName, Pageable pageable);

    Page<Product> findByProductNameContainingAndCategoryIdContaining(String productName, Categories categoryId, Pageable pageable);

    @Query("select p from Product p where p.appUsers = ?1")
    List<Product> findProductByAppUsers(AppUsers appUser);
}
