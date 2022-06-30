package com.secondhand.ecommerce.models.dto.products;

import com.secondhand.ecommerce.models.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.Validate;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private Long userId;
    private String username;
    private Long productId;
    private String productName;
    private String description;
    private BigInteger price;
    private Long imageId;
    private String urlImage;
    private Long categoryId;

    public static ProductDto productBuilder(Product product,
                                            Long userId,
                                            String email,
                                            Long categoryId,
                                            String urlImage) {

        Validate.notNull(product, "Product must not be null");

        return ProductDto.builder()
                .userId(userId)
                .username(email)
                .productId(product.getId())
                .productName(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
//                .imageId((product.getProductImage().getId()))
                .categoryId(categoryId)
                .urlImage(urlImage)
                .build();
    }
}
