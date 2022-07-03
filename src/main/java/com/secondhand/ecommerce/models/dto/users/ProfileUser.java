package com.secondhand.ecommerce.models.dto.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProfileUser implements Serializable {

    @JsonIgnore
    private Long userId;
    private String name;
    private String city;
    private String street;
    private String phoneNumber;
    private String imageProfile;

}
