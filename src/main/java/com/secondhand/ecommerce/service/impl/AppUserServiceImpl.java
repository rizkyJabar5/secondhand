package com.secondhand.ecommerce.service.impl;

import com.secondhand.ecommerce.exceptions.DuplicateDataExceptions;
import com.secondhand.ecommerce.models.dto.users.ProfileUser;
import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.repository.AppRolesRepository;
import com.secondhand.ecommerce.repository.AppUserRepository;
import com.secondhand.ecommerce.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.secondhand.ecommerce.utils.SecondHandConst.EMAIL_ALREADY_TAKEN;

@RequiredArgsConstructor
@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppRolesRepository roleRepository;

    private final AppUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public String registerNewUser(AppUsers appUsers) {

        validateDuplicateEmail(appUsers.getEmail());

        String encodePassword = passwordEncoder.encode(appUsers.getPassword());

        AppUsers requestUser = new AppUsers();
        requestUser.setFullName(appUsers.getFullName());
        requestUser.setEmail(appUsers.getEmail());
        requestUser.setPassword(encodePassword);

        userRepository.saveAndFlush(requestUser);

        getLogger().info("Create new user is successful");
        return "Create new user is successful";

    }

    @Override
    public AppUsers findUserByEmail(String email) {
        return null;
    }

    @Override
    public ProfileUser updateProfileUser(AppUsers appUsers) {
        return null;
    }

    private void validateDuplicateEmail(String email) {

        boolean present = userRepository.findByEmail(email).isPresent();
        if (present) {

            getLogger().info("{} has already taken by other user", email.toUpperCase());
            throw new DuplicateDataExceptions(String.format(EMAIL_ALREADY_TAKEN, email));

        }

    }
}
