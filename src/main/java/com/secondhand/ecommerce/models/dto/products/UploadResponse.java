package com.secondhand.ecommerce.models.dto.products;

import com.secondhand.ecommerce.models.enums.OperationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class UploadResponse {

    String message;

    @NonNull
    private OperationStatus status;

}
