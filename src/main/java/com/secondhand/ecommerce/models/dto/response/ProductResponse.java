package com.secondhand.ecommerce.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductResponse {
    private String username;
    private String productName;
    private String description;
    private BigInteger price;
    private String categoryName;
    private String createdBy;
    private String createdDate;
    private List<String> urlImageList;

    public ProductResponse(String productName,
                           String description,
                           BigInteger price,
                           String categoryName,
                           String createdBy,
                           String createdDate,
                           List<String> urlImageList) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.categoryName = categoryName;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.urlImageList = urlImageList;
    }
}
