package com.secondhand.ecommerce.service;

import com.secondhand.ecommerce.models.dto.products.ProductDto;
import com.secondhand.ecommerce.models.entity.Categories;
import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.utils.BaseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getProducts();

    Optional<Product> getProductById(Long id);

    BaseResponse addProduct(ProductDto product, MultipartFile[] image);

    BaseResponse updateProduct(ProductDto product, MultipartFile[] image);

    Optional<Product> deleteProductById(Long id);

    Page<Product> getProductsPage(String productName, Categories category, Pageable pageable);

}
