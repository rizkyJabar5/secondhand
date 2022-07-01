package com.secondhand.ecommerce.utils;

import com.secondhand.ecommerce.models.enums.OperationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class BaseResponse {

    private HttpStatus httpStatus;
    private String message;
    private Object data;
    private OperationStatus status;

}
