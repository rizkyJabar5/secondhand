package com.secondhand.ecommerce.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductRespone {
    private String username;
    private String productName;
    private String description;
    private BigInteger price;
    private String categoryName;
    private String createdBy;
    private String createdDate;
    private List<String> urlImageList;
}
