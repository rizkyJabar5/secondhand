package com.secondhand.ecommerce.exceptions;

public class DuplicateDataExceptions extends IllegalArgumentException {

    public DuplicateDataExceptions(String msg) {
        super(msg);
    }
}
