package com.secondhand.ecommerce.security.authentication.register;

import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.service.AppUserService;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final AppUserService userService;

    public RegisterService(AppUserService userService) {
        this.userService = userService;
    }

    public String registeredUser(RegisterRequest request) {

//        Todo: Validator email

        return userService.registerNewUser(
                new AppUsers(
                        request.getFullName(),
                        request.getEmail(),
                        request.getPassword()
                ));

    }
}
