package com.secondhand.ecommerce.service.impl;

import com.secondhand.ecommerce.exceptions.DuplicateDataExceptions;
import com.secondhand.ecommerce.models.dto.users.ProfileUser;
import com.secondhand.ecommerce.models.entity.AppRoles;
import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.models.enums.ERole;
import com.secondhand.ecommerce.repository.AppRolesRepository;
import com.secondhand.ecommerce.repository.AppUserRepository;
import com.secondhand.ecommerce.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.secondhand.ecommerce.utils.SecondHandConst.EMAIL_ALREADY_TAKEN;
import static com.secondhand.ecommerce.utils.SecondHandConst.EMAIL_NOT_FOUND_MSG;

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

        userRepository.save(requestUser);

        getLogger().info("Create new user is successful");
        return "Registered user is successful";

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
    public ProfileUser updateProfileUser(AppUsers appUsers) {


        ProfileUser profileUser = new ProfileUser();
        userRepository.findByUserId(profileUser.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User not found with ID: %s", profileUser.getUserId()))
                );

        appUsers.setFullName(profileUser.getName());
        appUsers.getAddress().setCity(profileUser.getCityName());
        appUsers.getAddress().setStreet(profileUser.getAddress());
        appUsers.setPhoneNumber(profileUser.getPhoneNumber());

        userRepository.save(appUsers);
        return profileUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUsers appUser = findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(
                                EMAIL_NOT_FOUND_MSG,
                                username))
                );

        getLogger().info("No user present with email: {} ", username);
        return new User(
                appUser.getUsername(),
                appUser.getPassword(),
                appUser.getAuthorities()
        );

    }

    private void validateDuplicateEmail(String email) {

        boolean present = userRepository.findByEmail(email).isPresent();
        if (present) {

            getLogger().info("{} has already taken by other user", email.toUpperCase());
            throw new DuplicateDataExceptions(String.format(EMAIL_ALREADY_TAKEN, email));

        }
    }

    private void addRoleToUsers(AppUsers users, Set<String> request) {

        Set<AppRoles> roles = new HashSet<>();

        if (request == null) {
            AppRoles defaultRole = roleRepository.findByRoleNames(ERole.SELLER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(defaultRole);
        } else {
            request.forEach(role -> {
                AppRoles allRoles = roleRepository.findByRoleNames(ERole.valueOf(role))
                        .orElseThrow(() -> new RuntimeException("Error: Role " + role + "  is not found"));
                roles.add(allRoles);
            });
        }

        users.setRoles(roles);
    }
}
