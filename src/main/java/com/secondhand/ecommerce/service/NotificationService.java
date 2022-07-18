package com.secondhand.ecommerce.service;


import com.secondhand.ecommerce.models.entity.Notification;
import com.secondhand.ecommerce.models.entity.Offers;
import com.secondhand.ecommerce.models.entity.Product;

import java.util.List;

public interface NotificationService {

    void saveNotification(String title, Offers offer, Product product, Long userId);

    void saveNotification(String title, Product product, Long userId);

    void updateIsRead(Long notifId);

    List<Notification> getNotification(Long userId);

}