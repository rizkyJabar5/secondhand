package com.secondhand.ecommerce.security.authentication.login;

import com.secondhand.ecommerce.security.SecurityUtils;
import com.secondhand.ecommerce.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.secondhand.ecommerce.utils.SecondHandConst.AUTHENTICATION_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTHENTICATION_URL)
public class LoginController {

//    private final EncryptionConfig encryptionConfig;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginJwtResponse> authenticationUserLogin(
            @Valid @RequestBody LoginRequest loginRequest) {

        String email = loginRequest.getEmail();
        SecurityUtils.authenticateUser(
                authenticationManager,
                email,
                loginRequest.getPassword()
        );
        SecurityUtils.authenticateUser(
                authenticationManager,
                email,
                loginRequest.getPassword());

        String jwtToken = jwtUtils.generateJwtToken(email);

        HttpHeaders responseHeaders = new HttpHeaders();

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(LoginJwtResponse.buildJwtResponse(jwtToken));
    }

    /**
     * Logout the user from the system and clear all cookies from request and response.
     *
     * @param request  the request
     * @param response the response
     * @return response entity
     */
//    @DeleteMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<LogoutResponse> logout(
//            HttpServletRequest request, HttpServletResponse response) {
//        SecurityUtils.logout(request, response);
//
//        HttpHeaders responseHeaders = cookieService.addDeletedCookieToHeaders(TokenType.REFRESH);
//        LogoutResponse logoutResponse = new LogoutResponse(OperationStatus.SUCCESS);
//        SecurityUtils.clearAuthentication();
//
//        return ResponseEntity.ok().headers(responseHeaders).body(logoutResponse);
//    }

//    private String updateCookies(String email, boolean isRefreshValid, HttpHeaders responseHeaders) {
//        return getCookies(email, isRefreshValid, responseHeaders, jwtUtils, encryptionConfig, cookieService);
//
//    }

    /**
     * Refreshes the current access token and refresh token accordingly.
     *
     * @param refreshToken The refresh token
     * @param request      The request
     * @return the jwt token details
     */
//    @GetMapping(value = "/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<LoginJwtResponse> refreshToken(
//            @CookieValue String refreshToken, HttpServletRequest request) {
//
//        String decryptedRefreshToken = encryptionConfig.decrypt(refreshToken);
//        boolean refreshTokenValid = jwtUtils.isValidJwtToken(decryptedRefreshToken);
//
//        if (!refreshTokenValid) {
//            throw new IllegalArgumentException("Invalid Token");
//        }
//        String email = jwtUtils.getUsernameFromToken(decryptedRefreshToken);
//        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//
//        SecurityUtils.validateUserDetailsStatus(userDetails);
//        SecurityUtils.authenticateUser(request, userDetails);
//
//        Date dateExpire = DateUtils.addMinutes(new Date(), 30);
//        String newAccessToken = jwtUtils.generateJwtToken(email, dateExpire);
//        String encrypt = encryptionConfig.encrypt(newAccessToken);
//
//        return ResponseEntity.ok(LoginJwtResponse.buildJwtResponse(encrypt));
//    }

}
