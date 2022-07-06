package com.secondhand.ecommerce.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductResponse {
    private Long productId;
    private String productName;
    private String description;
    private BigInteger price;
    private String categoryName;
    private String createdBy;
    private String createdDate;
    private List<String> urlImageList;
    private boolean isPublished;
    private boolean isSold;

    public ProductResponse(String productName,
                           String description,
                           BigInteger price,
                           String categoryName,
                           String createdBy,
                           String createdDate,
                           List<String> urlImageList,
                           boolean isPublished,
                           boolean isSold) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.categoryName = categoryName;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.urlImageList = urlImageList;
        this.isPublished = isPublished;
        this.isSold = isSold;
    }
}
