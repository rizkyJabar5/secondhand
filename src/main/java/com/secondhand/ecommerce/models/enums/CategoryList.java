package com.secondhand.ecommerce.models.enums;

public enum CategoryList {

    Hobi("Hobi"),
    Kendaraan("Kendaraan"),
    Baju("Baju"),
    Elektronik("Elektronik"),
    Kesehatan("Kesehatan");

    private final String name;

    CategoryList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
