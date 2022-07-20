package com.secondhand.ecommerce.models.dto.notification;

import com.secondhand.ecommerce.models.entity.Notification;
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
public class NotificationMapper {
    private Long notifeId;
    private Long productId;
    private Long userId;
    private String title;
    private String productName;
    private BigInteger price;
    private List<String> productImages;

    public NotificationMapper notifProduct(Notification entity) {
        notifeId = entity.getNotifId();
        productId = entity.getProduct().getId();
        userId = entity.getAppUser().getUserId();
        title = entity.getTitle();
        productName = entity.getProduct().getProductName();
        price = entity.getProduct().getPrice();
        productImages = entity.getProduct().getProductImages();
        return new NotificationMapper(notifeId, productId, userId, title, productName, price, productImages);
    }

}