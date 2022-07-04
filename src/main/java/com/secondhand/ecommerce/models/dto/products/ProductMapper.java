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
    private String publishedBy;
    private List<String> productImages;

    public ProductMapper productToDto(Product entity) {
        productId = entity.getId();
        productName = entity.getProductName();
        description = entity.getDescription();
        price = entity.getPrice();
        categoryId = entity.getCategory().getId();
        categoryName = entity.getCategory().getName().getName();
        publishedBy = entity.getCreatedBy();
        productImages = entity.getProductImages();

        return new ProductMapper(productId,
                productName,
                description,
                price,
                categoryId,
                categoryName,
                publishedBy,
                productImages);
    }
}
