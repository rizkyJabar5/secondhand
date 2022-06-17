package com.secondhand.ecommerce.security.authentication.login;

import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.security.filter.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class LoginController {

    private final AuthenticationManager authManager;

    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> postLogin(
            @Valid @RequestBody Map<String, Object> login) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.get("email"), login.get("password"))
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        AppUsers userDetails = (AppUsers) authentication.getPrincipal();


        return ResponseEntity.ok(
                new JwtResponse(
                        jwt, userDetails.getUserId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles)
        );
    }


}
