package com.secondhand.ecommerce.models.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Images{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id",unique=true, nullable = false)
    private Long imageId;

    @Column(name = "image_name")
    private String imageName;

    @Lob
    @Column(name = "image_file")
    private byte[] imageFile;

    public Long getId() {
        return imageId;
    }

    public void setId(Long imageId) {
        this.imageId = imageId;
    }

    public Images() {

    }

    public Images(String imageName, byte[] imageFile) {
        this.imageName = imageName;
        this.imageFile = imageFile;
    }
}
