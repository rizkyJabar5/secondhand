package com.secondhand.ecommerce.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenType {
    /**
     * The access token.
     */
    ACCESS("accessToken"),

    /**
     * The JSESSIONID token.
     */
    JSESSIONID("JSESSIONID"),

    /**
     * The refresh token
     */
    REFRESH("refreshToken");

    private final String name;
}
