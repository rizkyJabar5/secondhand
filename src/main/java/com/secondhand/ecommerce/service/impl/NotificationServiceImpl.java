package com.secondhand.ecommerce.service.impl;


import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.models.entity.Notification;
import com.secondhand.ecommerce.models.entity.Offers;
import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.repository.NotificationRepository;
import com.secondhand.ecommerce.service.AppUserService;
import com.secondhand.ecommerce.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final AppUserService usersService;

    @Override
    public void saveNotification(String title, Offers offer, Product product, Long userId) {
        Notification notification = new Notification();
        notification.setNotifId(notification.getNotifId());
        AppUsers users = usersService.findByUserId(userId);
        notification.setUserId(users);
        notification.setTitle(title);
        notification.setOfferId(offer);
        notification.setProductId(product);
        notificationRepository.save(notification);
    }

    @Override
    public void saveNotification(String title, Product product, Long userId) {
        Notification notification = new Notification();
        AppUsers users = usersService.findByUserId(userId);
        notification.setUserId(users);
        notification.setTitle(title);
        notification.setProductId(product);
        notificationRepository.save(notification);

    }

    @Override
    public void updateIsRead(Long notifId) {
        Optional<Notification> notification = notificationRepository.findById(notifId);
        notification.ifPresent(notification1 -> {
            notification1.setIsRead(true);
            notificationRepository.save(notification1);
        });
    }

    @Override
    public List<Notification> getNotification(Long userId) {
        return notificationRepository.findNotif(userId);
    }

}
