package com.secondhand.ecommerce.controller;

import com.secondhand.ecommerce.models.dto.response.CompletedResponse;
import com.secondhand.ecommerce.models.dto.users.ProfileUser;
import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.repository.AppUserRepository;
import com.secondhand.ecommerce.service.AppUserService;
import com.secondhand.ecommerce.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Tag(name = "Users Management", description = "Api endpoint for user to update their profile information, and check data")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class AppUserController {

    private final AppUserService userService;
    private final AppUserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Operation(summary = "Update existing user with id and equals principal")
    @PutMapping(value = "/profile-user",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> updateUserProfile(
            @Valid @ModelAttribute ProfileUser profileUser,
            @RequestParam(required = false) MultipartFile image) {

        BaseResponse response = userService.updateProfileUser(profileUser, image);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "check user profile for complete data")
    @GetMapping("/check-data-user/{id}")
    public ResponseEntity<?> getCheckProfileUsers(@PathVariable("id") Long userId) {

        CompletedResponse response = userService.checkProfileUser(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/change-password/{id}")
    public ResponseEntity<ResponseEntity> updateUsersPassword(
            @PathVariable("id") Long id,
            @RequestParam String oldPassword,
            @RequestParam String password,
            @RequestParam String retypePassword) {
        AppUsers users = userRepository.findUserByUserId(id);
        if (password.equals(retypePassword)) {
            if (passwordEncoder.matches(oldPassword, users.getPassword())) {
                userService.updateUsersPassword(password, id);
                return new ResponseEntity("Password berhasil diganti!", HttpStatus.OK);
            } else
                return new ResponseEntity("Password salah!", HttpStatus.BAD_REQUEST);

        } else
            return new ResponseEntity("Password harus sama", HttpStatus.BAD_REQUEST);
    }

}
