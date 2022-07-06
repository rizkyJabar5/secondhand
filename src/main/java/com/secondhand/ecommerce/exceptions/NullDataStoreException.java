package com.secondhand.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.NOT_FOUND,
        code = HttpStatus.NOT_FOUND,
        reason = "Your request is rejected.")
public class NullDataStoreException extends NullPointerException {
    public NullDataStoreException(String s) {
        super(s);
    }
}
