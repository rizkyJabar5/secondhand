package com.secondhand.ecommerce.repository;

import com.secondhand.ecommerce.models.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


}
