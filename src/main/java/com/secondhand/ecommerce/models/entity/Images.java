package com.secondhand.ecommerce.models.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Data
@Entity
public class Images extends AbstractEntity{

    @Column(name = "image_name")
    private String imageName;

    @Lob
    @Column(name = "image_file")
    private byte[] imageFile;

    public Images() {

    }

    public Images(String imageName, byte[] imageFile) {
        this.imageName = imageName;
        this.imageFile = imageFile;
    }
}
