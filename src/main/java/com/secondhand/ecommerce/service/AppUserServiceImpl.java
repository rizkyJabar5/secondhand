package com.secondhand.ecommerce.service;

import com.secondhand.ecommerce.models.dto.authentication.RegisterRequest;
import com.secondhand.ecommerce.models.dto.users.ProfileUser;
import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.repository.AppRolesRepository;
import com.secondhand.ecommerce.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppRolesRepository roleRepository;

    private final AppUserRepository userRepository;

    @Override
    public RegisterRequest registerNewUser(AppUsers appUsers) {

        getLogger().info("Create new user is successful");
        return null;

    }

    @Override
    public AppUsers findUserByEmail(String email) {
        return null;
    }

    @Override
    public ProfileUser updateProfileUser(AppUsers appUsers) {
        return null;
    }

}
