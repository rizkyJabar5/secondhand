package com.secondhand.ecommerce.service.impl;

import com.cloudinary.utils.ObjectUtils;
import com.secondhand.ecommerce.config.CloudinaryConfig;
import com.secondhand.ecommerce.exceptions.AppBaseException;
import com.secondhand.ecommerce.exceptions.DataViolationException;
import com.secondhand.ecommerce.exceptions.IllegalException;
import com.secondhand.ecommerce.models.dto.response.CompletedResponse;
import com.secondhand.ecommerce.models.dto.users.AppUserBuilder;
import com.secondhand.ecommerce.models.dto.users.ProfileUser;
import com.secondhand.ecommerce.models.entity.Address;
import com.secondhand.ecommerce.models.entity.AppRoles;
import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.models.enums.OperationStatus;
import com.secondhand.ecommerce.repository.AppRolesRepository;
import com.secondhand.ecommerce.repository.AppUserRepository;
import com.secondhand.ecommerce.security.SecurityUtils;
import com.secondhand.ecommerce.security.authentication.login.LoginRequest;
import com.secondhand.ecommerce.service.AppUserService;
import com.secondhand.ecommerce.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
                        String.format(EMAIL_NOT_FOUND_MSG, email.toUpperCase()))));
    }

    @Override
    public CompletedResponse checkProfileUser(Long userId) {
        boolean present = userRepository.findById(userId).isPresent();
        if (!present) {
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, userId));
        }

        userRepository.checkProfileUser(userId)
                .orElseThrow(() -> new AppBaseException("The user has not completed the profile"));

        return new CompletedResponse(
                "The User is completed to update their profile",
                OperationStatus.COMPLETED.getName());
    }

    @Override
    public BaseResponse updateProfileUser(ProfileUser profileUser, MultipartFile image) {

        AppUsers appUsers = userRepository.findByUserId(profileUser.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, profileUser.getUserId()))
                );

        AppUserBuilder builder = SecurityUtils.getAuthenticatedUserDetails();

        boolean equalsPrincipal = Objects.requireNonNull(builder)
                .getUserId()
                .equals(profileUser.getUserId());

        if (appUsers != null && equalsPrincipal) {

            if (appUsers.getAddress() == null) {
                Address address = new Address();
                address.setCity(profileUser.getCity());
                address.setStreet(profileUser.getStreet());
                appUsers.setAddress(address);
            }

            if (profileUser.getStreet() != null) {
                Address address = appUsers.getAddress();
                address.setStreet(profileUser.getStreet());
                address.setCity(address.getCity());
                appUsers.setAddress(address);
            }

            if (profileUser.getCity() != null) {
                Address address = appUsers.getAddress();
                address.setCity(profileUser.getCity());
                address.setStreet(address.getStreet());
                appUsers.setAddress(address);
            }

            if (profileUser.getName() != null) {
                appUsers.setFullName(profileUser.getName());
            }
            if (profileUser.getPhoneNumber() != null) {
                appUsers.setPhoneNumber(profileUser.getPhoneNumber());
            }
            if (image != null && !image.isEmpty()) {
                // Todo: ditambahkan argument untuk menghapus image profile user di cloudinary
                Map uploadResult = cloudinary.upload(image,
                        ObjectUtils.asMap("resourceType", "auto"));
                profileUser.setImageProfile(uploadResult.get("url").toString());
                appUsers.setImageUrl(profileUser.getImageProfile());
            }

            userRepository.save(appUsers);
        } else {
            throw new DataViolationException("You're not required to access this profile");
        }

        getLogger().info("User with email {} is successfully updated", builder.getEmail());
        return new BaseResponse(HttpStatus.ACCEPTED,
                "Success update profile user",
                profileUser,
                OperationStatus.SUCCESS);
    }

    @Override
    public Optional<AppUsers> loadUserById(Long id) {
        return Optional.ofNullable(userRepository.findByUserId(id))
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not found"));
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
                                username)));

        getLogger().info("User present with email: {} ", username);
        return AppUserBuilder.buildUserDetails(appUser);
    }

    private void validateDuplicateEmail(String email) {
        boolean present = userRepository.findByEmail(email).isPresent();
        if (present) {
            getLogger().info("{} has already taken by other user", email.toUpperCase());
            throw new IllegalException(EMAIL_ALREADY_TAKEN);
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
