package com.secondhand.ecommerce.security.authentication.register;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class RegisterRequest implements Serializable {

    @JsonProperty("fullName")
    @NotBlank(message = "Required field")
    private String fullName;

    @JsonProperty("email")
    @NotBlank(message = "E-mail not be empty.")
    @Email(message = "Email has been not valid")
    private String email;

    @JsonProperty("password")
    @NotBlank(message = "Password not be empty.")
//    @Size(min = 6)
    private String password;

}
