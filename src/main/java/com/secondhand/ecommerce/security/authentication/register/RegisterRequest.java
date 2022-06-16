package com.secondhand.ecommerce.security.authentication.register;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterRequest {

    @JsonProperty("fullName")
    @NotNull(message = "Required field")
    private String fullName;

    @JsonProperty("email")
    @NotNull(message = "E-mail not be empty.")
    @Email
    private String email;

    @JsonProperty("password")
    @NotNull
    @Size(min = 6)
    private String password;

}
