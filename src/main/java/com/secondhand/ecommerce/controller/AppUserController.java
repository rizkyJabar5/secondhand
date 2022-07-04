package com.secondhand.ecommerce.controller;

import com.secondhand.ecommerce.models.dto.response.CompletedResponse;
import com.secondhand.ecommerce.models.dto.users.ProfileUser;
import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.service.AppUserService;
import com.secondhand.ecommerce.utils.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Users Management", description = "Api endpoint for user to update their profile information, and check data")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class AppUserController {

    private final AppUserService userService;

    @PutMapping(value = "/profile-user",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> updateUserProfile(
            @Valid @ModelAttribute ProfileUser profileUser,
            @RequestParam(required = false) MultipartFile image) {

        BaseResponse response = userService.updateProfileUser(profileUser, image);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/check-data-user/{id}")
    public ResponseEntity<?> getCheckProfileUsers(@PathVariable("id") Long userId) {

        CompletedResponse response = userService.checkProfileUser(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<AppUsers>> getAllUsers(){
        List<AppUsers> users = userService.getAllUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }


}
