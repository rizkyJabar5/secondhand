package com.secondhand.ecommerce.security.jwt;


import com.secondhand.ecommerce.models.enums.TokenType;
import com.secondhand.ecommerce.utils.HasLogger;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

import static com.secondhand.ecommerce.utils.SecondHandConst.*;

@RequiredArgsConstructor
@Component
public class JwtUtils implements HasLogger {

    private static final String TOKEN_CREATED_SUCCESS = "Token successfully created as {}";
    private final SecretKey secretKey;

    public String generateJwtToken(String username) {
        Validate.notBlank(username, BLANK_USERNAME);

        return generateJwtToken(
                username,
                new Date(System.currentTimeMillis() + EXPIRATION_TIME_JWT)); // expired at 10 days
    }

    /**
     * Generate a JwtToken for the specified username.
     *
     * @param username   the username
     * @param expiration the expiration date
     * @return the token
     */
    public String generateJwtToken(String username, Date expiration) {
        Validate.notBlank(username, BLANK_USERNAME);

        String jwtToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(secretKey)
                .compact();

        getLogger().info(TOKEN_CREATED_SUCCESS, jwtToken);
        return jwtToken;
    }

    /**
     * Retrieve username from the token.
     *
     * @param token the token
     * @return the username
     */
    public String getUsernameFromToken(String token) {
        Validate.notBlank(token, "Token cannot be blank");

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Retrieves the jwt token from the request cookie or request header if present and valid.
     *
     * @param request    the httpRequest
     * @param fromCookie if jwt should be retrieved from the cookies.
     * @return the jwt token
     */
    public String getJwtToken(HttpServletRequest request, boolean fromCookie) {
        if (fromCookie) {
            return getJwtFromCookie(request);
        }

        return getJwtFromRequest(request);
    }

    /**
     * Validates the Jwt token passed to it.
     *
     * @param token the token
     * @return if valid or not
     */
    public boolean isValidJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            getLogger().error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            getLogger().error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            getLogger().error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            getLogger().error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            getLogger().error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    /**
     * Retrieves the jwt token from the request header if present and valid.
     *
     * @param request the httpRequest
     * @return the jwt token
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isNotBlank(headerAuth)
                && headerAuth.startsWith(TOKEN_PREFIX)) {
            return headerAuth.split(StringUtils.SPACE)[10]; // 10 day of expired at token
        }
        return null;
    }

    /**
     * Retrieves the jwt token from the request cookie if present and valid.
     *
     * @param request the httpRequest
     * @return the jwt token
     */
    private String getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.nonNull(cookies)) {
            for (Cookie cookie : cookies) {
                if (TokenType.ACCESS.getName().equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
