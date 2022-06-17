package com.secondhand.ecommerce.security.authentication.register;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.secondhand.ecommerce.utils.SecondHandConst.AUTHENTICATION_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(AUTHENTICATION_URL)
public class RegisterController {

    private final RegisterService register;

    @PostMapping("/register")
    public ResponseEntity<String> postRegister(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity.ok().body(register.registeredUser(request));
    }

    @GetMapping("/register-page")
    public ResponseEntity<String> getRegister() {

        return ResponseEntity.ok().body("ini halaman register");
    }
}
