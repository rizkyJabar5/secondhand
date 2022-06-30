package com.secondhand.ecommerce.security.authentication.register;

import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.service.AppUserService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RegisterService {

    private final AppUserService userService;

    public RegisterService(AppUserService userService) {
        this.userService = userService;
    }

    public void registeredUser(RegisterRequest request) {

        AppUsers user = new AppUsers(
                request.getFullName(),
                request.getEmail(),
                request.getPassword()
                );
        userService.registerNewUser(user);
    }
}
