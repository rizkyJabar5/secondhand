package com.secondhand.ecommerce.service;

import com.secondhand.ecommerce.models.dto.products.ProductDto;
import com.secondhand.ecommerce.models.dto.products.ProductMapper;
import com.secondhand.ecommerce.models.dto.response.CompletedResponse;
import com.secondhand.ecommerce.models.entity.Categories;
import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.utils.BaseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ProductService {

    BaseResponse getProductsByUserId(Long userId);

    BaseResponse getAllProducts();

    BaseResponse addProduct(ProductDto product, MultipartFile[] image);

    BaseResponse updateProduct(ProductDto product, MultipartFile[] image);

    CompletedResponse deleteProductById(Long id);

    Page<Product> getProductsPage(String productName, Categories category, Pageable pageable);

    Page<Product> getSortedPaginatedProducts(int page, int limit, Sort sort);

    Optional<ProductMapper> loadProductById(Long id);

}
