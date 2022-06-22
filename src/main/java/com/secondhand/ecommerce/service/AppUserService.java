package com.secondhand.ecommerce.service;

import com.secondhand.ecommerce.models.dto.users.ProfileUser;
import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.security.authentication.login.LoginRequest;
import com.secondhand.ecommerce.utils.HasLogger;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface AppUserService extends UserDetailsService, HasLogger {

    LoginRequest registerNewUser(AppUsers appUsers);

    Optional<AppUsers> findUserByEmail(String email);

    ProfileUser updateProfileUser(AppUsers appUsers);

}
