package com.secondhand.ecommerce.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    private String name;
    private String description;
    private Long price;

    @Column(name = "image_name")
    private String imageName;

    @Lob
    @Column(name = "image_file")
    private byte[] imageFile;

    @Column(name = "image_url")
    private String url;

    public String getUrl() {
        return url;
    }

    public Long getId() {
        return productId;
    }
    public void setId(Long id) {
        this.productId = productId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getPrice() {
        return price;
    }
    public void setPrice(Long price) {
        this.price = price;
    }
    public String getImageName(){
        return imageName;
    }
    public byte[] getImageFile(){
        return imageFile;
    }

    public void product (String imageName, byte[] imageFile) {
        this.imageName = imageName;
        this.imageFile = imageFile;
    }

    public void product(String url) {
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
