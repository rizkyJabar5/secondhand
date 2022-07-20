package com.secondhand.ecommerce.models.dto.products;

import com.secondhand.ecommerce.models.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ProductMapper {

    private Long productId;
    private String productName;
    private String description;
    private BigInteger price;
    private Long categoryId;
    private String categoryName;
    private String addedBy;
    private String userName;
    private String city;
    private String photoProfile;
    private boolean isPublished;
    private boolean isSold;
    private List<String> productImages;

    public ProductMapper productToDto(Product entity) {
        productId = entity.getId();
        productName = entity.getProductName();
        description = entity.getDescription();
        price = entity.getPrice();
        categoryId = entity.getCategory().getId();
        categoryName = entity.getCategory().getName().getName();
        addedBy = entity.getCreatedBy();
        userName = entity.getAppUsers().getFullName();
        if (city != null) {
            city = entity.getAppUsers().getAddress().getCity();
        }
        if (photoProfile != null) {
            photoProfile = entity.getAppUsers().getImageUrl();
        }
        isPublished = entity.getIsPublished();
        isSold = entity.getIsSold();
        productImages = entity.getProductImages();

        return new ProductMapper(productId,
                productName,
                description,
                price,
                categoryId,
                categoryName,
                addedBy,
                userName,
                city,
                photoProfile,
                isPublished,
                isSold,
                productImages);
    }
}
