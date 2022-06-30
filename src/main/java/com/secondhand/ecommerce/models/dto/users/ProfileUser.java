package com.secondhand.ecommerce.models.dto.users;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProfileUser implements Serializable {

    private Long userId;
    private String name;
    private String city;
    private String street;
    private String phoneNumber;

}
