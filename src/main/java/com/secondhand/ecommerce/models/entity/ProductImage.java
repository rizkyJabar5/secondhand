package com.secondhand.ecommerce.models.entity;

import lombok.Data;

import javax.persistence.*;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class ProductImage extends BaseEntity {

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "url_file")
    private String urlFile;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product_image")
    private List<Product> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProductImage that = (ProductImage) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}