package com.secondhand.ecommerce.controller;

import com.secondhand.ecommerce.models.dto.response.CompletedResponse;
import com.secondhand.ecommerce.models.dto.users.ProfileUser;
import com.secondhand.ecommerce.models.dto.users.UpdatePasswordRequest;
import com.secondhand.ecommerce.service.AppUserService;
import com.secondhand.ecommerce.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Tag(name = "Users Management", description = "Api endpoint for user to update their profile information, and check data")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class AppUserController {

    private final AppUserService userService;

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

    @Operation(summary = "Change password user")
    @PostMapping("/change-password/{userId}")
    public ResponseEntity<BaseResponse> updateUsersPassword(
            @PathVariable("userId") Long userId,
            UpdatePasswordRequest passwordRequest) {

        BaseResponse response = userService.updateUsersPassword(userId, passwordRequest);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
