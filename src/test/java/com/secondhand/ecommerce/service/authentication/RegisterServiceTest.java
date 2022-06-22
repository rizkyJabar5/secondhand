package com.secondhand.ecommerce.service.authentication;

import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.security.authentication.login.LoginRequest;
import com.secondhand.ecommerce.security.authentication.register.RegisterRequest;
import com.secondhand.ecommerce.security.authentication.register.RegisterService;
import com.secondhand.ecommerce.service.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceTest {

    @Mock
    AppUserService userService;

    @Mock
    PasswordEncoder passwordEncoder;

    RegisterService registerService;

    @BeforeEach
    void setUp() {
        registerService = new RegisterService(userService);
    }

    @Test
    void registerService() {

        RegisterRequest request = new RegisterRequest();
        request.setEmail("slsls@mail.com");
        request.setPassword("mushsssss");
        request.setFullName("rizky");

        LoginRequest loginRequest = new LoginRequest(
                request.getEmail(),
                request.getPassword()
        );

        AppUsers newUser = new AppUsers(
                request.getFullName(),
                request.getEmail(),
                request.getPassword()
        );

        Mockito.when(userService.registerNewUser(newUser)).thenReturn(loginRequest);

        registerService.registeredUser(request);

        Mockito.verify(userService).registerNewUser(newUser);

    }
}
