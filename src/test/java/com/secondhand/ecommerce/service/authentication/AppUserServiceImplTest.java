package com.secondhand.ecommerce.service.authentication;

import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.repository.AppRolesRepository;
import com.secondhand.ecommerce.repository.AppUserRepository;
import com.secondhand.ecommerce.security.authentication.register.RegisterRequest;
import com.secondhand.ecommerce.service.AppUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ExtendWith({MockitoExtension.class})
class AppUserServiceImplTest {


    @MockBean
    AppUserRepository userRepository;

    @Mock
    AppRolesRepository rolesRepository;

    @MockBean
    PasswordEncoder passwordEnconder;

    @Autowired
    AppUserService userService;


    @Test
    void registerNewUser() {

        RegisterRequest request = new RegisterRequest();
        request.setFullName("Rizky Abdul Jabar");
        request.setEmail("email@mail.com");
        request.setPassword("alamatku");

        String encode = passwordEnconder.encode(request.getPassword());

        AppUsers newUser = new AppUsers(
                request.getFullName(),
                request.getEmail(),
                encode
        );
        given(userRepository.save(any(AppUsers.class))).willReturn(newUser);

        String msg = String.valueOf(userService.registerNewUser(newUser));

        assertEquals("Create new user is successful", msg);

    }

    @Test
    void findUserByEmail() {
    }

    @Test
    void updateProfileUser() {
    }
}