package com.secondhand.ecommerce.models.dto.users;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class ProfileUser implements Serializable {

    private Long userId;
    private String name;
    private String cityName;
    private String address;
    private Integer phoneNumber;
    private MultipartFile picture;
}