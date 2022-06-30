package com.secondhand.ecommerce.models.dto.products;

import com.secondhand.ecommerce.models.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.Validate;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@Builder
public class ProductRequest {

    private Long userId;
    private String email;
    private Long productId;
    private String productName;
    private String description;
    private BigInteger price;
    private Long imageId;
    private String urlFile;
    private Long categoryId;
    private String categoryName;

    public static ProductRequest productBuilder(Product product) {

        Validate.notNull(product, "Product must not be null");

        return ProductRequest.builder()
                .userId(product.getAppUsers().getUserId())
                .email(product.getAppUsers().getFullName())
                .productId(product.getId())
                .productName(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageId((product.getProductImage().getId()))
                .urlFile(product.getProductImage().getUrlFile())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .build();
    }
}
