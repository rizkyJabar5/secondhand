package com.secondhand.ecommerce.utils;

import java.util.regex.Pattern;

public class SecondHandConst {

    public static final String EMAIL_ALREADY_TAKEN = "E-mail has already taken by other user";

    public static final String EMAIL_NOT_FOUND_MSG = "E-mail: %s is not found.";
    public static final String ROLE_IS_NOT_FOUND_MSG = "Error: Role %s is not found";
    public static final String USER_NOT_FOUND_MSG = "Error: User %s is not found";
    public static final String PRODUCT_NOT_FOUND_MSG = "Error: Product %s is not found";

    public static final long EXPIRATION_TIME_JWT = 864000000; // 10 days

    public static final String BLANK_USERNAME = "Email tidak boleh kosong.";

    public static final String TOKEN_PREFIX = "Bearer ";

    //List url
    public static final String AUTHENTICATION_URL = "/api/v1/auth";

    public static final String SWAGGER_API_DOCS = "/v3/api-docs/**";
    public static final String SWAGGER_API = "/swagger-ui/**";
    public static final String HOME_PAGE = "/api/v1/home";

    public static final String TITLE_NOTIFICATION = "Penawaran Produk";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


}
