package com.secondhand.ecommerce.models.dto.authentication;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RegisterRequest implements Serializable {

    private String fullName;
    private String email;
    private String password;
}
