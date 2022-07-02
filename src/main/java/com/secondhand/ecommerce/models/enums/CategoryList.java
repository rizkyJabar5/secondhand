package com.secondhand.ecommerce.models.enums;

public enum CategoryList {

    HOBI("Hobi"),
    KENDARAAN("Kendaraan"),
    BAJU("Baju"),
    ELEKTRONIK("Elektronik"),
    KESEHATAN("Kesehatan");

    private final String name;

    CategoryList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
