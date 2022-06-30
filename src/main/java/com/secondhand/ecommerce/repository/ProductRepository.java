package com.secondhand.ecommerce.repository;

import com.secondhand.ecommerce.models.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query(value = "delete from product where productId =:productId", nativeQuery = true)
    void deleteByProductId(@Param("productId")long productId);

    Optional<Product> findByProductId(long productId);

    Page<Product> findByNameIgnoreCaseAndCategoryIgnoreCase(String name, String category, Pageable pageable);
}
