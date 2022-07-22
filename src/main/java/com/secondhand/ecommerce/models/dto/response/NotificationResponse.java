package com.secondhand.ecommerce.models.dto.response;

import com.secondhand.ecommerce.models.entity.Notification;
import com.secondhand.ecommerce.models.entity.Offers;
import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.utils.DateUtilConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class NotificationResponse {
    private Long notifId;
    private String title;
    private Boolean isRead;
    private BigInteger offerNegotiated;
    private String productName;
    private BigInteger price;
    private String url;
    private Long userId;
    private String createdDate;

    public NotificationResponse(Notification notification, Product product, Offers offer) {
        Date date = notification.getCreatedDate();
        LocalDateTime localDateTime = DateUtilConverter.toLocalDate(date);

        this.notifId = notification.getNotifId();
        this.title = notification.getTitle();
        this.isRead = notification.getIsRead();
        this.offerNegotiated = offer.getOfferNegotiated();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.url = product.getProductImages().get(0);
        this.userId = notification.getAppUser().getUserId();
        this.createdDate = localDateTime.toString();
    }

    public NotificationResponse(Notification notification, Product product) {
        Date date = notification.getCreatedDate();
        LocalDateTime localDateTime = DateUtilConverter.toLocalDate(date);

        this.notifId = notification.getNotifId();
        this.title = notification.getTitle();
        this.isRead = notification.getIsRead();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.url = product.getProductImages().get(0);
        this.userId = notification.getAppUser().getUserId();
        this.createdDate = localDateTime.toString();
    }
}
