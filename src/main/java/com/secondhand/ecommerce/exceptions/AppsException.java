package com.secondhand.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.INTERNAL_SERVER_ERROR,
        code = HttpStatus.INTERNAL_SERVER_ERROR,
        reason = "Your request is not found.")
public class AppsException extends Exception {

    public AppsException(String msg) {
        super(msg);
    }

    public AppsException(String message, Throwable cause) {
        super(message, cause);
    }
}
