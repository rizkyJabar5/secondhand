package com.secondhand.ecommerce.models.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "product_image")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "url_file")
    private String urlFile;

    public ProductImage(String urlFile) {
        this.urlFile = urlFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProductImage that = (ProductImage) o;
        return getImageId() != null && Objects.equals(getImageId(), that.getImageId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}