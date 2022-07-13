package com.secondhand.ecommerce.service;

import com.secondhand.ecommerce.models.dto.products.ProductDto;
import com.secondhand.ecommerce.models.dto.products.ProductMapper;
import com.secondhand.ecommerce.models.dto.products.ProductUpdate;
import com.secondhand.ecommerce.models.dto.response.CompletedResponse;
import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.utils.BaseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ProductService {

    BaseResponse getProductsByUserId(Long userId);

    BaseResponse addProduct(ProductDto product, MultipartFile[] image);

    BaseResponse updateProduct(ProductUpdate product, MultipartFile... image);

    CompletedResponse deleteProductById(Long id);

    BaseResponse publishedProduct(Long productId);

    BaseResponse getProductIsSoldByUser(Long userId);

    Page<Product> getSortedPaginatedProducts(int page, int limit, Sort sort);

    Optional<ProductMapper> loadProductById(Long id);

    Page<ProductMapper> getAllProductPageByProductNameAndCategory(String productName, Long categoryId, Pageable paging);

    Product getProductById(Long productId);
}
