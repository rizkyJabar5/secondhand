package com.secondhand.ecommerce.service.impl;

import com.secondhand.ecommerce.exceptions.DuplicateDataExceptions;
import com.secondhand.ecommerce.models.dto.users.AppUserBuilder;
import com.secondhand.ecommerce.models.dto.users.ProfileUser;
import com.secondhand.ecommerce.models.entity.AppRoles;
import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.repository.AppRolesRepository;
import com.secondhand.ecommerce.repository.AppUserRepository;
import com.secondhand.ecommerce.security.authentication.login.LoginRequest;
import com.secondhand.ecommerce.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.secondhand.ecommerce.utils.SecondHandConst.EMAIL_ALREADY_TAKEN;
import static com.secondhand.ecommerce.utils.SecondHandConst.EMAIL_NOT_FOUND_MSG;

@RequiredArgsConstructor
@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppRolesRepository roleRepository;

    private final AppUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginRequest registerNewUser(AppUsers appUsers) {

        validateDuplicateEmail(appUsers.getEmail());

        String encodePassword = passwordEncoder.encode(appUsers.getPassword());

        AppUsers requestUser = new AppUsers();
        requestUser.setFullName(appUsers.getFullName());
        requestUser.setEmail(appUsers.getEmail());
        requestUser.setPassword(encodePassword);
        addRoleToUsers(requestUser, appUsers.getRoles());

        userRepository.save(requestUser);

        getLogger().info("Create new user is successful");
        return new LoginRequest(
                requestUser.getEmail(),
                requestUser.getPassword()
        );
    }

    @Override
    public Optional<AppUsers> findUserByEmail(String email) {

        getLogger().error("Username {} is not found. Please create one", email);
        return Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(EMAIL_NOT_FOUND_MSG, email.toUpperCase()))
                ));

    }

    @Override
    public ProfileUser updateProfileUser(ProfileUser profileUser) {


//        ProfileUser profileUser = new ProfileUser();
//        userRepository.findByUserId(profileUser.getUserId())
//                .orElseThrow(() -> new UsernameNotFoundException(
//                        String.format("User not found with ID: %s", profileUser.getUserId()))
//                );
//
//        appUsers.setFullName(profileUser.getName());
//        appUsers.getAddress().setCity(profileUser.getCityName());
//        appUsers.getAddress().setStreet(profileUser.getAddress());
//        appUsers.setPhoneNumber(profileUser.getPhoneNumber());
//        appUsers.setImageName(profileUser.getPicture(imageName));

//        profileUserRepository.save(profileUser);
        return profileUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (StringUtils.isAnyBlank(username)) {
            throw new UsernameNotFoundException("Email must be provided");
        }

        getLogger().info("No user present with email: {} ", username);
        AppUsers appUser = findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(
                                EMAIL_NOT_FOUND_MSG,
                                username))
                );

        return AppUserBuilder.buildUserDetails(appUser);

    }

    private void validateDuplicateEmail(String email) {

        boolean present = userRepository.findByEmail(email).isPresent();
        if (present) {

            getLogger().info("{} has already taken by other user", email.toUpperCase());
            throw new DuplicateDataExceptions(EMAIL_ALREADY_TAKEN);

        }
    }

    private void addRoleToUsers(AppUsers users, Collection<AppRoles> request) {

        List<AppRoles> roles = new ArrayList<>();
        if (request == null) {
            List<AppRoles> allRoles = roleRepository.findAll();
            roles.addAll(allRoles);
        }

        users.setRoles(roles);
    }
}
