package com.secondhand.ecommerce.models.dto.products;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUpdate {

    private Long productId;
    private String productName;
    private String description;
    private BigInteger price;
    private Long categoryId;

}
