package com.secondhand.ecommerce.security.authentication.register;


import com.secondhand.ecommerce.security.SecurityUtils;
import com.secondhand.ecommerce.security.authentication.login.LoginJwtResponse;
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

@RequiredArgsConstructor
@RestController
@RequestMapping(AUTHENTICATION_URL)
public class RegisterController {

    private final RegisterService register;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> postRegister(
            @Valid @RequestBody RegisterRequest request) {

        register.registeredUser(request);

        String email = request.getEmail();
        SecurityUtils.authenticateUser(
                authenticationManager,
                email,
                request.getPassword());

        String jwtToken = jwtUtils.generateJwtToken(email);

        HttpHeaders httpHeaders = new HttpHeaders();

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(LoginJwtResponse.buildJwtResponse(jwtToken));
    }

}
