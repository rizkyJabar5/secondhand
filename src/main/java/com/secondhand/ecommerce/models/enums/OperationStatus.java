package com.secondhand.ecommerce.models.enums;

public enum OperationStatus {
    /**
     * If an operation is successful.
     */
    SUCCESS("Success"),

    /**
     * If an operation is unsuccessful.
     */
    FAILURE("Failure"),
    /**
     * If an operation is completed.
     */
    COMPLETED("Completed"),
    /**
     * If an operation is not completed.
     */
    NOT_COMPLETED("Not Completed"),
    /**
     * If an operation is Found.
     */
    NOT_FOUND("Not Found"),
    /**
     * If an operation is not Found.
     */
    FOUND("Found");

    private final String name;

    OperationStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
