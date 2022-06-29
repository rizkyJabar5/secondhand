package com.secondhand.ecommerce.models.dto.products;

import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.models.entity.Categories;
import com.secondhand.ecommerce.models.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.Validate;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ProductMapper {

    private Long userId;
    private String userFullName;
    private Long productId;
    private String productName;
    private String description;
    private BigInteger price;
    private Long imageId;
    private String urlFile;
    private Long categoryId;
    private String categoryName;
    private List<String> listUrl;

    public static ProductMapper productResponse(Product product) {

        Validate.notNull(product, "Product must not be null");
//        List<String> url = new ArrayList<>();

        return ProductMapper.builder()
                .userId(product.getAppUsers().getUserId())
                .userFullName(product.getAppUsers().getFullName())
                .productId(product.getId())
                .productName(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageId((product.getProductImage().getId()))
                .urlFile(product.getProductImage().getUrlFile())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
//                .listUrl(url)
                .build();
    }

    public Product addProduct() {

        Product product = new Product();

        AppUsers appUsers = new AppUsers();
        appUsers.setUserId(userId);
        product.setAppUsers(appUsers);

        product.setName(productName);
        product.setDescription(description);
        product.setPrice(price);

//        ProductImage productImage = new ProductImage();
//        productImage.setId(imageId);
//        productImage.setUrlFile(urlFile);
//        product.setProductImage(productImage);

        Categories categories = new Categories();
        categories.setId(categoryId);
        product.setCategory(categories);

        return product;
    }
}
