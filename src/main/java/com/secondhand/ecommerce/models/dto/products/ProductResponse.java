


package com.secondhand.ecommerce.models.dto.products;

import com.secondhand.ecommerce.models.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.Validate;

import java.math.BigInteger;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ProductResponse {

    private Long userId;
    private Long productId;
    private String userFullName;
    private String productName;
    private String description;
    private BigInteger price;
    private Long categoryId;
    private String category;
    private List<String> url;

    public static ProductResponse buildProductDetail(Product product) {

        Validate.notNull(product, "Product must not be null");

        return ProductResponse.builder()
                .userId(product.getAppUsers().getUserId())
                .userFullName(product.getAppUsers().getFullName())
                .productId(product.getId())
                .productName(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .categoryId(product.getCategory().getId())
                .category(product.getCategory().getName())
                .build();
    }

}

