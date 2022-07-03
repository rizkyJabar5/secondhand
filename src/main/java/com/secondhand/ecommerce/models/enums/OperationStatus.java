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

    NOTCOMPLETED("Not Completed");

    private final String name;

    OperationStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
