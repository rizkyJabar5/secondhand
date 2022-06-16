package com.secondhand.ecommerce.service.authentication;

import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.security.authentication.register.RegisterRequest;
import com.secondhand.ecommerce.security.authentication.register.RegisterService;
import com.secondhand.ecommerce.service.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceTest {

    @Mock
    AppUserService userService;

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
        Mockito.when(userService.registerNewUser(new AppUsers(
                request.getFullName(),
                request.getEmail(),
                request.getPassword()
        ))).thenReturn("Pendaftaran berhasil");

        registerService.registeredUser(request);

        Mockito.verify(userService).registerNewUser(new AppUsers(
                request.getFullName(),
                request.getEmail(),
                request.getPassword()));

    }
}
