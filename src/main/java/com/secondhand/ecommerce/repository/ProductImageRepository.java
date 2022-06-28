package com.secondhand.ecommerce.repository;

import com.secondhand.ecommerce.models.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
