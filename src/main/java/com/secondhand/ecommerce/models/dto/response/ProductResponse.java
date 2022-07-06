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
}
