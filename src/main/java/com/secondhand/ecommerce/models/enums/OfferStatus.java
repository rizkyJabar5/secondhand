package com.secondhand.ecommerce.models.enums;

public enum OfferStatus {

    Waiting("Waiting"),
    Accepted("Accepted"),
    Rejected("Rejected"),
    Done("Done");

    private String name;

    OfferStatus(String name) {
        this.name = name;
    }
}
