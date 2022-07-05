package com.secondhand.ecommerce.repository;

import com.secondhand.ecommerce.models.entity.Categories;
import com.secondhand.ecommerce.models.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByProductNameIgnoreCaseAndCategoryIgnoreCase(String productName, Categories categoryId, Pageable pageable);

    @Query("select  p from Product p where p.category.id =:categoryId")
    Page<Product> findByCategoryIdContaining(Long categoryId, Pageable pageable);

    @NonNull
    @Query("select p from Product p where p.isPublished = true")
    Page<Product> findAll(@NonNull Pageable pageable);

    @Query("select p from Product p where p.productName like concat('%', ?1, '%') and p.isPublished = true")
    Page<Product> findByProductNameContaining(String productName, Pageable pageable);

    @Query("select p from Product p where p.productName =:productName and p.category.id =:categoryId and p.isPublished = true")
    Page<Product> findByProductNameContainingAndCategoryIdContaining(String productName, Long categoryId, Pageable pageable);

    @Query("select p from Product p where p.appUsers.userId = ?1")
    List<Product> findProductByAppUsers(Long user);
}
