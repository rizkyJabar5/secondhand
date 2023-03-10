package com.secondhand.ecommerce.repository;

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

    @Query("select  p from Product p " +
            "where p.category.id =:categoryId " +
            "and p.isPublished = true " +
            "and p.isSold = false ")
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    @Query("select  p from Product p " +
            "where p.category.id =:categoryId " +
            "and not p.appUsers.userId =:userId " +
            "and p.isPublished = true " +
            "and p.isSold = false ")
    Page<Product> findByCategoryIdWithUserId(Long categoryId, Long userId, Pageable pageable);

    @NonNull
    @Query("select p from Product p " +
            "where p.isPublished = true " +
            "and p.isSold = false")
    Page<Product> findAll(@NonNull Pageable pageable);

    @NonNull
    @Query("select p from Product p " +
            "where not p.appUsers.userId =?1 and " +
            "p.isPublished = true " +
            "and p.isSold = false")
    Page<Product> findAllWithUserId(Long userId, @NonNull Pageable pageable);

    @Query("select p from Product p " +
            "where upper (p.productName) like upper (concat('%', ?1, '%'))" +
            "and p.isPublished = true " +
            "and p.isSold = false")
    Page<Product> findByProductName(String productName, Pageable pageable);

    @Query("select p from Product p " +
            "where not p.appUsers.userId =?1 and " +
            "upper (p.productName) like upper (concat('%', ?2, '%'))" +
            "and p.isPublished = true " +
            "and p.isSold = false")
    Page<Product> findByProductNameWithUserId(Long userId, String productName, Pageable pageable);

    @Query("select p from Product p " +
            "where upper(p.productName) like upper(concat('%', ?1, '%')) " +
            "and p.category.id = ?2 " +
            "and p.isPublished = true " +
            "and p.isSold = false")
    Page<Product> findByProductNameContainingIgnoreCaseAndCategoryId(String productName, Long categoryId, Pageable pageable);

    @Query("select p from Product p " +
            "where upper(p.productName) like upper(concat('%', :productName, '%')) " +
            "and p.category.id = :categoryId " +
            "and not p.appUsers.userId = :userId " +
            "and p.isPublished = true " +
            "and p.isSold = false")
    Page<Product> findByProductNameAndCategoryIdWithUserId(String productName, Long categoryId, Long userId, Pageable pageable);

    @Query("select p from Product p where p.appUsers.userId = ?1 and p.isSold = false")
    List<Product> findProductByAppUsers(Long user);

    @Query("select p from Product p where p.appUsers.userId = ?1 and p.isSold = true")
    List<Product> findProductIsSoldByUsers(Long userId);

    @Query("select count(p) from Product p where p.appUsers.userId = ?1 and p.isSold = false")
    Long countByAppUsers(Long userId);

    @Query(value =
            "select DISTINCT ON (products.id) products.* " +
                    "from products " +
                    "inner join offers " +
                    "on offers.product_id = products.id " +
                    "where products.user_id=?1", nativeQuery = true)
    List<Product> findByUserId(Long userId);

}
