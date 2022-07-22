package com.secondhand.ecommerce.service.authentication;

import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.repository.AppRolesRepository;
import com.secondhand.ecommerce.repository.AppUserRepository;
import com.secondhand.ecommerce.security.authentication.login.LoginRequest;
import com.secondhand.ecommerce.security.authentication.register.RegisterRequest;
import com.secondhand.ecommerce.service.AppUserService;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith({MockitoExtension.class})
class AppUserServiceImplTest {

    @MockBean
    AppUserRepository userRepository;

    @Mock
    AppRolesRepository rolesRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    AppUserService userService;


    @Test
    void registerNewUser() {
        Faker FAKER = new Faker();

        RegisterRequest request = new RegisterRequest();
        request.setFullName(FAKER.name().fullName());
        request.setEmail(FAKER.internet().emailAddress());
        request.setPassword(FAKER.internet().password());

        String encode = passwordEncoder.encode(request.getPassword());

        AppUsers newUser = new AppUsers(
                request.getFullName(),
                request.getEmail(),
                encode
        );
        given(userRepository.save(any(AppUsers.class))).willReturn(newUser);

        LoginRequest loginRequest = userService.registerNewUser(newUser);

        assertNotNull(loginRequest);

    }

    @Test
    void findUserByEmail() {
    }

    @Test
    void updateProfileUser() {
    }
}