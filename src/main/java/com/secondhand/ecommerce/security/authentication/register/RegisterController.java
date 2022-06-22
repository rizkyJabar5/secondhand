package com.secondhand.ecommerce.security.authentication.register;


import com.secondhand.ecommerce.security.CookieService;
import com.secondhand.ecommerce.security.SecurityUtils;
import com.secondhand.ecommerce.security.authentication.login.LoginJwtResponse;
import com.secondhand.ecommerce.security.config.EncryptionConfig;
import com.secondhand.ecommerce.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.secondhand.ecommerce.security.authentication.CookieService.getCookies;
import static com.secondhand.ecommerce.utils.SecondHandConst.AUTHENTICATION_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(AUTHENTICATION_URL)
public class RegisterController {

    private final RegisterService register;
    private final JwtUtils jwtUtils;
    private final CookieService cookieService;
    private final EncryptionConfig encryptionConfig;
    private final AuthenticationManager authenticationManager;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> postRegister(
            @CookieValue(required = false) String refreshToken,
            @Valid @RequestBody RegisterRequest request) {

        register.registeredUser(request);
        SecurityUtils.authenticateUser(
                authenticationManager,
                request.getEmail(),
                request.getPassword());

        String decryptRefreshJwt = encryptionConfig.decrypt(refreshToken);
        boolean isRefreshValidJwtToken = jwtUtils.isValidJwtToken(decryptRefreshJwt);
        HttpHeaders responseHeaders = new HttpHeaders();

        String newAccessToken = updateCookies(request.getEmail(), isRefreshValidJwtToken, responseHeaders);
        String encryptAccessToken = encryptionConfig.encrypt(newAccessToken);

        HttpHeaders httpHeaders = new HttpHeaders();

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(LoginJwtResponse.buildJwtResponse(encryptAccessToken));
    }

    @GetMapping("/register-page")
    public ResponseEntity<String> getRegister() {

        return ResponseEntity.ok().body("ini halaman register");
    }

    private String updateCookies(String email, boolean isRefreshValid, HttpHeaders responseHeaders) {
        return getCookies(email,
                isRefreshValid,
                responseHeaders,
                jwtUtils,
                encryptionConfig,
                cookieService);
    }
}
