package com.secondhand.ecommerce.service;

import com.secondhand.ecommerce.models.dto.authentication.RegisterRequest;
import com.secondhand.ecommerce.models.dto.users.ProfileUser;
import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.utils.HasLogger;

public interface AppUserService extends HasLogger {

    RegisterRequest registerNewUser(AppUsers appUsers);

    AppUsers findUserByEmail(String email);

    ProfileUser updateProfileUser(AppUsers appUsers);

}
