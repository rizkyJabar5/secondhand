package com.secondhand.ecommerce.models.dto.response;

import com.secondhand.ecommerce.models.entity.Notification;
import com.secondhand.ecommerce.models.entity.Offers;
import com.secondhand.ecommerce.models.entity.Product;
import lombok.Data;

import java.math.BigInteger;

@Data
public class NotificationResponse {
    private Long notifId;
    private String title;
    private Boolean isRead;
    private BigInteger offerNegotiated;
    private String productName;
    private BigInteger price;
    private String url;
    private Long userId;

    public NotificationResponse() {

    }

    public NotificationResponse(Notification notification, Product product, Offers offer) {
        this.notifId = notification.getNotifId();
        this.title = notification.getTitle();
        this.isRead = notification.getIsRead();
        this.offerNegotiated = offer.getOfferNegotiated();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.url = product.getProductImages().get(0);
        this.userId = notification.getUserId().getUserId();
    }

    public NotificationResponse(Notification notification, Product product) {
        this.notifId = notification.getNotifId();
        this.isRead = notification.getIsRead();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.url = product.getProductImages().get(0);
        this.userId = notification.getUserId().getUserId();
    }
}