package com.secondhand.ecommerce.utils;

import java.util.regex.Pattern;

public class SecondHandConst {

    public static final String EMAIL_ALREADY_TAKEN = "%s has already taken by other user";

    public static final String USERNAME_NOT_FOUND_MSG = "Username with %s is not found.";
    public static final String ROLE_IS_NOT_FOUND_MSG = "Error: Role %s is not found";

    public static final long EXPIRATION_TIME_JWT = 864000000; // 10 days
    public static final String DELETING_SELF_NOT_PERMITTED_MSG = "Pengguna terblokir! Silahkan hubungi Administrator.";
    public static final String MODIFY_LOCKED_USER_NOT_PERMITTED_MSG = "Pengguna terkunci! Tidak bisa diubah ataupun dihapus";

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


}
