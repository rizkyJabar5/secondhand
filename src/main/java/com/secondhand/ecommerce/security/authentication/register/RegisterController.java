package com.secondhand.ecommerce.security.authentication.register;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class RegisterController {

    private final RegisterService register;

    @PostMapping("/register")
    public ResponseEntity<String> postRegister(RegisterRequest request) {

        return ResponseEntity.ok().body(register.registeredUser(request));
    }

    @GetMapping("/register-page")
    public ResponseEntity<String> getRegister() {

        return ResponseEntity.ok().body("ini halaman register");
    }
}
