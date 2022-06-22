package com.secondhand.ecommerce.security.authentication.login;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @JsonProperty("email")
    @NotBlank(message = "E-mail not be empty.")
    @Email(message = "Email has been not valid")
    private String email;

    @JsonProperty("password")
    @NotBlank
    @Size(min = 6)
    private String password;
}
