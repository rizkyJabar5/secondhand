package com.secondhand.ecommerce.models.enums;

import java.util.Arrays;

public enum ERole {

    BUYER("buyer"),
    SELLER("seller");

    private final String roleName;

    ERole(String roleName) {
        this.roleName = roleName;
    }

    public static String getAllRoles() {
        return Arrays.toString(ERole.class.getEnumConstants());
    }
}
