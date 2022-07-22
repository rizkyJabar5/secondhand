package com.secondhand.ecommerce.service;


import com.secondhand.ecommerce.models.dto.response.CompletedResponse;
import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.models.entity.Notification;
import com.secondhand.ecommerce.models.entity.Offers;
import com.secondhand.ecommerce.models.entity.Product;

import java.util.List;

public interface NotificationService {

    void saveNotification(String title, Offers offer, Product product, AppUsers users);

    void saveNotification(String title, Product product, AppUsers users);

    CompletedResponse updateIsRead(Long notifId);

    List<Notification> getNotification(Long userId);

}
