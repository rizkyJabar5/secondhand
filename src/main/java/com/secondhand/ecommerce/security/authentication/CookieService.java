package com.secondhand.ecommerce.security.authentication;

import com.secondhand.ecommerce.models.enums.TokenType;
import com.secondhand.ecommerce.security.config.EncryptionConfig;
import com.secondhand.ecommerce.security.jwt.JwtUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpHeaders;

import java.time.Duration;
import java.util.Date;

public class CookieService {

    public static String getCookies(String email,
                                    boolean isRefreshValid,
                                    HttpHeaders responseHeaders,
                                    JwtUtils jwtUtils,
                                    EncryptionConfig encryptionConfig,
                                    com.secondhand.ecommerce.security.CookieService cookieService) {
        if (!isRefreshValid) {
            String token = jwtUtils.generateJwtToken(email);
            Duration refreshDuration = Duration.ofDays(10);

            String encrypt = encryptionConfig.encrypt(token);
            cookieService.addCookieToHeaders(
                    responseHeaders,
                    TokenType.REFRESH,
                    encrypt,
                    refreshDuration);
        }

        Date accessTokenExpiration = DateUtils.addMinutes(new Date(), 30);
        return jwtUtils.generateJwtToken(email, accessTokenExpiration);
    }

}
