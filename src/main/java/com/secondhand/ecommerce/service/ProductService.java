package com.secondhand.ecommerce.service;

import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.models.entity.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface ProductService {
    List<Product> getProducts();

    Optional<Product> getProductById(Long id);

    Product addProduct(Product product);

    Product updateProduct(Product product);

    Optional<Product> deleteProductById(Long id);

    ProductImage saveProductImage(ProductImage productImage);

    Page<Product> getSortedPaginatedProducts(int page, int limit, Sort by);

    Optional<Product> deleteProductByProductId(long productId);
}
