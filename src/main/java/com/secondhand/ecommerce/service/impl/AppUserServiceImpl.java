package com.secondhand.ecommerce.service.impl;

import com.cloudinary.utils.ObjectUtils;
import com.secondhand.ecommerce.config.CloudinaryConfig;
import com.secondhand.ecommerce.exceptions.AppBaseException;
import com.secondhand.ecommerce.exceptions.DataViolationException;
import com.secondhand.ecommerce.exceptions.DuplicateDataExceptions;
import com.secondhand.ecommerce.models.dto.users.AppUserBuilder;
import com.secondhand.ecommerce.models.dto.users.ProfileUser;
import com.secondhand.ecommerce.models.entity.Address;
import com.secondhand.ecommerce.models.entity.AppRoles;
import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.repository.AppRolesRepository;
import com.secondhand.ecommerce.repository.AppUserRepository;
import com.secondhand.ecommerce.security.SecurityUtils;
import com.secondhand.ecommerce.security.authentication.login.LoginRequest;
import com.secondhand.ecommerce.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static com.secondhand.ecommerce.utils.SecondHandConst.*;

@RequiredArgsConstructor
@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppRolesRepository roleRepository;

    private final AppUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final CloudinaryConfig cloudinary;

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

        return Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(EMAIL_NOT_FOUND_MSG, email.toUpperCase()))
                ));
    }

    @Override
    public AppUsers checkProfileUser(Long userId) {
        return userRepository.checkProfileUser(userId);
    }

    @Override
    public ProfileUser updateProfileUser(ProfileUser profileUser, MultipartFile image) {

        AppUsers appUsers = userRepository.findByUserId(profileUser.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, profileUser.getUserId()))
                );

        AppUserBuilder builder = SecurityUtils.getAuthenticatedUserDetails();

        boolean equalsPrincipal = Objects.requireNonNull(builder)
                .getUserId()
                .equals(profileUser.getUserId());

        if (appUsers != null && equalsPrincipal) {
            Address address = new Address();
            appUsers.setFullName(profileUser.getName());
            address.setCity(profileUser.getCity());
            address.setStreet(profileUser.getStreet());
            appUsers.setAddress(address);
            appUsers.setPhoneNumber(profileUser.getPhoneNumber());

            try {
                Map uploadResult = cloudinary.upload(image.getBytes(),
                        ObjectUtils.asMap("resourceType", "auto"));
                profileUser.setImageProfile(uploadResult.get("url").toString());
                appUsers.setImageUrl(uploadResult.get("url").toString());
            } catch (IOException e) {
                throw new AppBaseException("Upload failed", e);
            }

            userRepository.save(appUsers);
        } else {
            throw new DataViolationException("You're not required to access this profile");
        }

        getLogger().info("User with email {} is successfully updated", builder.getEmail());
        return profileUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (StringUtils.isAnyBlank(username)) {
            throw new UsernameNotFoundException("Email must be provided");
        }

        AppUsers appUser = findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(
                                EMAIL_NOT_FOUND_MSG,
                                username))
                );
        getLogger().info("User present with email: {} ", username);
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
