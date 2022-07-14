package com.secondhand.ecommerce.models.dto.notification;

import lombok.Data;

@Data
public class NotificationRequestDto {

    private String target;
    private String title;
    private String body;
}