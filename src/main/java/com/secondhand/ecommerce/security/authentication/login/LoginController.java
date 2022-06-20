package com.secondhand.ecommerce.security.authentication.login;

import com.secondhand.ecommerce.models.enums.OperationStatus;
import com.secondhand.ecommerce.models.enums.TokenType;
import com.secondhand.ecommerce.security.CookieService;
import com.secondhand.ecommerce.security.SecurityUtils;
import com.secondhand.ecommerce.security.authentication.LogoutResponse;
import com.secondhand.ecommerce.security.config.EncryptionConfig;
import com.secondhand.ecommerce.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.Duration;
import java.util.Date;

import static com.secondhand.ecommerce.utils.SecondHandConst.AUTHENTICATION_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTHENTICATION_URL)
public class LoginController {

    private final UserDetailsService userDetailsService;

    private final EncryptionConfig encryptionConfig;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final CookieService cookieService;


    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<LoginJwtResponse> authenticationUserLogin(
            @CookieValue(required = false) String refreshToken,
            @Valid @RequestBody LoginRequest loginRequest) {

        String email = loginRequest.getEmail();
        SecurityUtils.authenticateUser(authenticationManager, email, loginRequest.getPassword());

        String decryptRefreshJwt = encryptionConfig.decrypt(refreshToken);
        boolean isRefreshValidJwtToken = jwtUtils.isValidJwtToken(decryptRefreshJwt);
        HttpHeaders responseHeaders = new HttpHeaders();

        String newAccessToken = updateCookies(email, isRefreshValidJwtToken, responseHeaders);
        String encryptAccessToken = encryptionConfig.encrypt(newAccessToken);

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(LoginJwtResponse.buildJwtResponse(encryptAccessToken));
    }

    private String updateCookies(String email, boolean isRefreshValid, HttpHeaders responseHeaders) {
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

    /**
     * Refreshes the current access token and refresh token accordingly.
     *
     * @param refreshToken The refresh token
     * @param request      The request
     * @return the jwt token details
     */
    @GetMapping(value = "/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginJwtResponse> refreshToken(
            @CookieValue String refreshToken, HttpServletRequest request) {

        String decryptedRefreshToken = encryptionConfig.decrypt(refreshToken);
        boolean refreshTokenValid = jwtUtils.isValidJwtToken(decryptedRefreshToken);

        if (!refreshTokenValid) {
            throw new IllegalArgumentException("Invalid Token");
        }
        String email = jwtUtils.getUsernameFromToken(decryptedRefreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        SecurityUtils.validateUserDetailsStatus(userDetails);
        SecurityUtils.authenticateUser(request, userDetails);

        Date dateExpire = DateUtils.addMinutes(new Date(), 30);
        String newAccessToken = jwtUtils.generateJwtToken(email, dateExpire);
        String encrypt = encryptionConfig.encrypt(newAccessToken);

        return ResponseEntity.ok(LoginJwtResponse.buildJwtResponse(encrypt));
    }

    /**
     * Logout the user from the system and clear all cookies from request and response.
     *
     * @param request  the request
     * @param response the response
     * @return response entity
     */
    @DeleteMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LogoutResponse> logout(
            HttpServletRequest request, HttpServletResponse response) {
        SecurityUtils.logout(request, response);

        HttpHeaders responseHeaders = cookieService.addDeletedCookieToHeaders(TokenType.REFRESH);
        LogoutResponse logoutResponse = new LogoutResponse(OperationStatus.SUCCESS);
        SecurityUtils.clearAuthentication();

        return ResponseEntity.ok().headers(responseHeaders).body(logoutResponse);
    }

}
