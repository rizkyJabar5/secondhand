package com.secondhand.ecommerce.models.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_image_id")
    private Long productImageId;

    @Column(name = "product_image_name")
    private String productImageName;

    @Column(name = "product_image_file")
    private byte[] productImageFile;

    @Column(name = "product_url")
    private String[] url;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product productId;

    public ProductImage(){

    }

    public void setUrl(String[] url) {
        this.url = url;
    }
}