package com.secondhand.ecommerce.service.impl;


import com.secondhand.ecommerce.models.dto.response.CompletedResponse;
import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.models.entity.Notification;
import com.secondhand.ecommerce.models.entity.Offers;
import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.models.enums.OperationStatus;
import com.secondhand.ecommerce.repository.NotificationRepository;
import com.secondhand.ecommerce.service.AppUserService;
import com.secondhand.ecommerce.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final AppUserService usersService;

    @Override
    public void saveNotification(String title, Offers offer, Product product, AppUsers users) {

        Notification notification = new Notification();

        notification.setAppUser(users);
        notification.setTitle(title);
        notification.setOffers(offer);
        notification.setProduct(product);
        notificationRepository.save(notification);
    }

    @Override
    public void saveNotification(String title, Product product, AppUsers users) {

        Notification notification = new Notification();

        notification.setAppUser(users);
        notification.setTitle(title);
        notification.setProduct(product);

        notificationRepository.save(notification);
    }

    @Override
    public CompletedResponse updateIsRead(Long notifId) {
        Notification notification = notificationRepository.findById(notifId)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));
        notification.setIsRead(true);
        notificationRepository.save(notification);

        return new CompletedResponse(
                "Notification read successfully",
                OperationStatus.SUCCESS.getName()
        );

    }

    @Override
    public List<Notification> getNotification(Long userId) {
        return notificationRepository.findNotificationUser(userId);
    }

}
