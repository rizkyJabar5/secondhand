


package com.secondhand.ecommerce.models.dto.products;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
    private Long price;
    private Long categoryId;
    private String category;
    private List<String> url;

    public ProductResponse(Long userId, String name, String description, Long price, String category, List<String> asList) {
        this.userId = userId;
        this.productName = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.url = asList;
    }

//    public static ProductResponse buildProductDetail(Product product) {
//
//        Validate.notNull(product, "Product must not be null");
//
//        return ProductResponse.builder()
//                .userId(product.getAppUsers().getUserId())
//                .userFullName(product.getAppUsers().getFullName())
//                .productId(product.getId())
//                .productName(product.getName())
//                .description(product.getDescription())
//                .price(product.getPrice())
//                .category(product.getName())
//                .build();
//    }

}

