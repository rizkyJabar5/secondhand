package com.secondhand.ecommerce.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.NOT_ACCEPTABLE,
        code = HttpStatus.NOT_ACCEPTABLE,
        reason = "Your request is rejected.")
public class DataViolationException extends DataIntegrityViolationException {
    public DataViolationException(String msg) {
        super(msg);
    }

    public DataViolationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
