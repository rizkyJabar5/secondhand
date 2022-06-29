package com.secondhand.ecommerce.controller;

import com.secondhand.ecommerce.models.dto.response.CompletedResponse;
import com.secondhand.ecommerce.models.dto.users.ProfileUser;
import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.models.enums.OperationStatus;
import com.secondhand.ecommerce.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class AppUserController {

    private final AppUserService userService;

    @PutMapping("/profile-user")
    public ResponseEntity<Map<String, Object>> updateUserProfile(
            @Valid @RequestBody ProfileUser profileUser) {

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("success", true);
        response.put("data", userService.updateProfileUser(profileUser));

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

    }

    @GetMapping("/check-data-user/{id}")
    public ResponseEntity<Object> getCheckProfileUsers(@PathVariable("id") Long userId) {

        Map<String, Object> response = new HashMap<>();
        AppUsers value = userService.checkProfileUser(userId);

        if (value == null) {
            CompletedResponse completedResponse = new CompletedResponse(OperationStatus.COMPLETED);
            return new ResponseEntity<>(completedResponse, HttpStatus.OK);
        } else {
            response.put("success", true);
            response.put("data", value);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}