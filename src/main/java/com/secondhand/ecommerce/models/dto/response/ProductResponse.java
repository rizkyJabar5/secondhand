package com.secondhand.ecommerce.models.dto.response;

import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.utils.DateUtilConverter;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
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

    public ProductResponse(Product product) {
        Date date = product.getCreatedDate();
        LocalDateTime localDateTime = DateUtilConverter.toLocalDate(date);
        String formatDate = localDateTime.format(DateUtilConverter.formatter());

        this.productId = product.getId();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.categoryName = product.getCategory().getName().getName();
        this.createdBy = product.getCreatedBy();
        this.createdDate = formatDate;
        this.urlImageList = product.getProductImages();
        this.isPublished = product.getIsPublished();
        this.isSold = product.getIsSold();
    }
}
