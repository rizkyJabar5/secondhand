package com.secondhand.ecommerce.models.dto.users;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.io.Serializable;

@Data
public class ProfileUser implements Serializable {

    private Long userId;
    private String name;
    private String cityName;
    private String address;
    private Integer phoneNumber;
    @Column(name = "image_name")
    private String imageName;

    @Lob
    @Column(name = "image_file")
    private byte[] imageFile;

    @Column(name = "image_url")
    private String url;

    public String getImageName(){
        return imageName;
    }
    public byte[] getImageFile(){
        return imageFile;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void AppUsers(String url){
        this.url = url;
    }
    public void AppUsers(String imageName, byte[] imageFile) {
        this.imageName = imageName;
        this.imageFile = imageFile;
    }
}
