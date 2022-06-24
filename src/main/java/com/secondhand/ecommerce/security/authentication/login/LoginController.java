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


}
