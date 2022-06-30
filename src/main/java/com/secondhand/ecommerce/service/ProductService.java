package com.secondhand.ecommerce.service;

import com.secondhand.ecommerce.models.dto.products.ProductRequest;
import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.models.entity.ProductImage;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getProducts();

    Optional<Product> getProductById(Long id);

    ProductRequest addProduct(Product product);

    Product updateProduct(Product product);

    Optional<Product> deleteProductById(Long id);

    ProductImage saveProductImage(ProductImage productImage);
}
