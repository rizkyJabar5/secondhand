package com.secondhand.ecommerce.models.dto.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class UpdatePasswordRequest {
    @JsonProperty("password")
    @NotBlank
    @Size(min = 6)
    private String password;
}
