package com.secondhand.ecommerce.service;

import com.secondhand.ecommerce.models.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getProducts();
    Optional<Product> getProductById(Long id);

    Product addProduct(Product product);
    Optional<Product> deleteProductById(Long id);
}
