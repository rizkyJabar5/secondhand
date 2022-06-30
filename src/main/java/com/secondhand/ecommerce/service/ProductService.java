package com.secondhand.ecommerce.service;

import com.secondhand.ecommerce.models.dto.products.ProductDto;
import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.models.entity.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getProducts();

    Optional<Product> getProductById(Long id);

    ProductDto addProduct(ProductDto product, MultipartFile[] image);

    Product updateProduct(Product product);

    Optional<Product> deleteProductById(Long id);

    ProductImage saveProductImage(ProductImage productImage);
}
