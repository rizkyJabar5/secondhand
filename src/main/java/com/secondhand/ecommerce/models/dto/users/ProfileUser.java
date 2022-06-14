package com.secondhand.ecommerce.models.dto.users;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class ProfileUser implements Serializable {

    private String name;
    private String cityName;
    private String address;
    private String phoneNumber;
    private MultipartFile picture;
}
