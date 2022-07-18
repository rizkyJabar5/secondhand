package com.secondhand.ecommerce.controller;

import com.secondhand.ecommerce.models.dto.response.MessageResponse;
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
                            if (notification.getOfferId() == null) {
                                return new NotificationResponse(notification, notification.getProductId());
                            } else
                                return new NotificationResponse(notification, notification.getProductId(), notification.getOfferId());
                        }).collect(Collectors.toList());
        return new ResponseEntity<>(notificationResponses, HttpStatus.OK);
    }

    @PostMapping("/read/{notifId}")
    public ResponseEntity<MessageResponse> readNotif(@PathVariable Long notifId) {
        notificationService.updateIsRead(notifId);
        return ResponseEntity.ok(new MessageResponse("Notification read successfully"));
    }

}
