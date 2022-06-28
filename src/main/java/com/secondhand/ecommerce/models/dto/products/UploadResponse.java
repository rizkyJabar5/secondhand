package com.secondhand.ecommerce.models.dto.products;

import lombok.Data;

@Data
public class UploadResponse {
    String message;
    String[] url;
}
