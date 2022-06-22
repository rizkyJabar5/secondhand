package com.secondhand.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.BAD_REQUEST,
        code = HttpStatus.BAD_REQUEST,
        reason = "Your request is rejected.")
public class DuplicateDataExceptions extends IllegalArgumentException {

    public DuplicateDataExceptions(String msg) {
        super(msg);
    }
}
