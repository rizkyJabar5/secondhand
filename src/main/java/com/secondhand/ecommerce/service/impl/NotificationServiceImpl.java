package com.secondhand.ecommerce.service.impl;


import com.secondhand.ecommerce.models.dto.users.AppUserBuilder;
import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.models.entity.Notification;
import com.secondhand.ecommerce.models.entity.Offers;
import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.repository.NotificationRepository;
import com.secondhand.ecommerce.security.SecurityUtils;
import com.secondhand.ecommerce.service.AppUserService;
import com.secondhand.ecommerce.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.secondhand.ecommerce.utils.SecondHandConst.EMAIL_NOT_FOUND_MSG;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final AppUserService usersService;

    @Override
    public void saveNotification(String title, Offers offer, Product product) {
        AppUserBuilder builder = SecurityUtils.getAuthenticatedUserDetails();
        boolean authenticated = SecurityUtils.isAuthenticated();
        AppUsers appUsers = usersService.findUserByEmail(Objects.requireNonNull(builder).getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(EMAIL_NOT_FOUND_MSG, builder.getEmail())));
        if (authenticated) {
            Notification notification = new Notification();
            
            notification.setUserId(appUsers);
            notification.setTitle(title);
            notification.setOfferId(offer);
            notification.setProductId(product);

            notificationRepository.save(notification);
        }

    }

    @Override
    public void saveNotification(String title, Product product) {
        AppUserBuilder builder = SecurityUtils.getAuthenticatedUserDetails();
        boolean authenticated = SecurityUtils.isAuthenticated();
        AppUsers appUsers = usersService.findUserByEmail(Objects.requireNonNull(builder).getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(EMAIL_NOT_FOUND_MSG, builder.getEmail())));

        if (authenticated) {
            Notification notification = new Notification();

            notification.setUserId(appUsers);
            notification.setTitle(title);
            notification.setProductId(product);

            notificationRepository.save(notification);
        }

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
