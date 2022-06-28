package com.secondhand.ecommerce.models.dto.products;

import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {
    private Long userId;
    private Long productId;
    private String name;
    private String description;
    private Long price;
    private String category;
    private List<String> url;

    public ProductResponse() {}

    public ProductResponse(Long userId, Long productId, String name, String description,
                           Long price, String category, List<String> url){
        this.userId = userId;
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.url = url;
    }
}
