package com.secondhand.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.BAD_REQUEST,
        code = HttpStatus.BAD_REQUEST,
        reason = "Your request is rejected.")
public class AppBaseException extends RuntimeException {

    public AppBaseException(String message) {
        super(message);
    }

    public AppBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
