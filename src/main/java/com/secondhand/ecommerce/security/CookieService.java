package com.secondhand.ecommerce.security;

import com.secondhand.ecommerce.models.enums.TokenType;
import com.secondhand.ecommerce.utils.HasLogger;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CookieService implements HasLogger {

    /**
     * The token type cannot be null.
     */
    public static final String THE_TOKEN_TYPE_CANNOT_BE_NULL = "The tokenType cannot be null";

    /**
     * The token cannot be null or empty
     */
    public static final String THE_TOKEN_CANNOT_BE_NULL_OR_EMPTY =
            "The token cannot be null or empty";

    private final Environment environment;

    private final Duration duration = Duration.ofDays(7);

    /**
     * Creates a servlet cookie from spring httpCookie.
     *
     * @param httpCookie the httpCookie
     * @return the cookie
     */
    public Cookie createCookie(HttpCookie httpCookie) {
        Validate.notNull(httpCookie, "The httpCookie cannot be null");

        Cookie cookie = new Cookie(httpCookie.getName(), httpCookie.getValue());
        cookie.setSecure(
                Arrays.asList(environment.getActiveProfiles()).contains("prod"));
        cookie.setHttpOnly(true);

        return cookie;
    }

    /**
     * Creates an httpOnly httpCookie.
     *
     * @param name     the name of the cookie
     * @param value    the value of the cookie
     * @param duration the duration till expiration
     * @return the cookie
     */
    public HttpCookie createCookie(String name, String value, Duration duration) {
        Validate.notBlank(name, "The name cannot be null or empty");

        return ResponseCookie.from(name, value)
                .secure(Arrays.asList(environment.getActiveProfiles()).contains("prod"))
                .sameSite("strict")
                .path("/")
                .maxAge(Objects.isNull(duration) ? this.duration : duration)
                .httpOnly(true)
                .build();
    }

    /**
     * Creates a cookie with the specified token and token type.
     *
     * @param token     the token
     * @param tokenType the type of token
     * @return the cookie
     */
    public HttpCookie createTokenCookie(String token, TokenType tokenType) {
        Validate.notBlank(token, THE_TOKEN_CANNOT_BE_NULL_OR_EMPTY);

        return createTokenCookie(token, tokenType, duration);
    }

    /**
     * Creates a cookie with the specified token and token type.
     *
     * @param token     the token
     * @param tokenType the type of token
     * @param duration  the duration till expiration
     * @return the cookie
     */
    public HttpCookie createTokenCookie(String token, TokenType tokenType, Duration duration) {
        Validate.notBlank(token, THE_TOKEN_CANNOT_BE_NULL_OR_EMPTY);
        Validate.notNull(tokenType, THE_TOKEN_TYPE_CANNOT_BE_NULL);

        return createCookie(tokenType.getName(), token, duration);
    }

    /**
     * Creates a cookie with the specified token.
     *
     * @param tokenType the token type
     * @return the cookie
     */
    public HttpCookie deleteTokenCookie(TokenType tokenType) {
        Validate.notNull(tokenType, THE_TOKEN_TYPE_CANNOT_BE_NULL);

        return createCookie(tokenType.getName(), StringUtils.EMPTY, Duration.ZERO);
    }

    /**
     * Creates a cookie with the specified token.
     *
     * @param tokenType the token type
     * @return the httpHeaders
     */
    public HttpHeaders addDeletedCookieToHeaders(TokenType tokenType) {
        Validate.notNull(tokenType, THE_TOKEN_TYPE_CANNOT_BE_NULL);

        HttpCookie httpCookie = deleteTokenCookie(tokenType);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, httpCookie.toString());
        return httpHeaders;
    }

    /**
     * creates a cookie with the specified token and tokenType then adds it to headers.
     *
     * @param tokenType the tokenType
     * @param token     the token
     * @return the httpHeaders
     */
    public HttpHeaders addCookieToHeaders(TokenType tokenType, String token) {
        Validate.notNull(tokenType, THE_TOKEN_TYPE_CANNOT_BE_NULL);
        Validate.notBlank(token, THE_TOKEN_CANNOT_BE_NULL_OR_EMPTY);

        return addCookieToHeaders(tokenType, token, duration);
    }

    /**
     * creates a cookie with the specified token and tokenType then adds it to headers.
     *
     * @param tokenType the tokenType
     * @param token     the token
     * @param duration  the duration till expiration
     * @return the httpHeaders
     */
    public HttpHeaders addCookieToHeaders(TokenType tokenType, String token, Duration duration) {
        Validate.notNull(tokenType, THE_TOKEN_TYPE_CANNOT_BE_NULL);
        Validate.notBlank(token, THE_TOKEN_CANNOT_BE_NULL_OR_EMPTY);

        HttpHeaders httpHeaders = new HttpHeaders();
        addCookieToHeaders(
                httpHeaders, tokenType, token, Objects.isNull(duration) ? this.duration : duration);

        return httpHeaders;
    }

    /**
     * creates a cookie with the specified token and type with duration then adds it to the headers.
     *
     * @param httpHeaders the httpHeaders
     * @param tokenType   the tokenType
     * @param token       the token
     * @param duration    the duration till expiration
     */
    public void addCookieToHeaders(
            HttpHeaders httpHeaders, TokenType tokenType, String token, Duration duration) {
        Validate.notNull(tokenType, THE_TOKEN_TYPE_CANNOT_BE_NULL);

        httpHeaders.add(
                HttpHeaders.SET_COOKIE, createTokenCookie(token, tokenType, duration).toString());
    }
}
