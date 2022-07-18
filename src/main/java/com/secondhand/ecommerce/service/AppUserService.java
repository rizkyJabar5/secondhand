package com.secondhand.ecommerce.service;

import com.secondhand.ecommerce.models.dto.response.CompletedResponse;
import com.secondhand.ecommerce.models.dto.users.ProfileUser;
import com.secondhand.ecommerce.models.dto.users.UpdatePasswordRequest;
import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.security.authentication.login.LoginRequest;
import com.secondhand.ecommerce.utils.BaseResponse;
import com.secondhand.ecommerce.utils.HasLogger;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface AppUserService extends UserDetailsService, HasLogger {

    LoginRequest registerNewUser(AppUsers appUsers);

    Optional<AppUsers> findUserByEmail(String email);

    CompletedResponse checkProfileUser(Long UserId);

    BaseResponse updateProfileUser(ProfileUser profileUser, MultipartFile image);

    AppUsers loadUserById(Long id);

    BaseResponse updateUsersPassword(Long userId, UpdatePasswordRequest passwordRequest);
}
