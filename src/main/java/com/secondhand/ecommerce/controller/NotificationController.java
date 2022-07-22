package com.secondhand.ecommerce.controller;

import com.secondhand.ecommerce.models.dto.response.CompletedResponse;
import com.secondhand.ecommerce.models.dto.response.NotificationResponse;
import com.secondhand.ecommerce.models.entity.Notification;
import com.secondhand.ecommerce.service.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Notification", description = "API for processing various operations with Notification entity")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/get/{userId}")
    public ResponseEntity<List<NotificationResponse>> getNotification(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getNotification(userId);
        List<NotificationResponse> notificationResponses =
                notifications.stream()
                        .map(notification -> {
                            if (notification.getOffers() == null) {
                                return new NotificationResponse(notification, notification.getProduct());
                            }
                            return new NotificationResponse(notification, notification.getProduct(), notification.getOffers());
                        }).collect(Collectors.toList());
        return new ResponseEntity<>(notificationResponses, HttpStatus.OK);
    }

    @PutMapping("/read/{notifId}")
    public ResponseEntity<CompletedResponse> readNotif(@PathVariable Long notifId) {
        CompletedResponse response = notificationService.updateIsRead(notifId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
