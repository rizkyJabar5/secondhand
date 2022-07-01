package com.secondhand.ecommerce.models.dto.response;

import com.secondhand.ecommerce.models.enums.OperationStatus;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
public class CompletedResponse implements Serializable {

    private String completed = " User is completed to update their profile";

    @NonNull
    private OperationStatus status;
}
