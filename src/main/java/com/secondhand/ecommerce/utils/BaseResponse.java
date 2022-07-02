package com.secondhand.ecommerce.utils;

import com.secondhand.ecommerce.models.enums.OperationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class BaseResponse {

    private Date timestamp = new Date(System.currentTimeMillis());
    private HttpStatus httpStatus;
    private String message;
    private Object data;
    private OperationStatus status;

    public BaseResponse(Date timestamp, String message, OperationStatus status) {
        this.timestamp = timestamp;
        this.message = message;
        this.status = status;
    }

    public BaseResponse(HttpStatus httpStatus, String message, Object data) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(HttpStatus httpStatus, String message, Object data, OperationStatus status) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.data = data;
        this.status = status;
    }
}
