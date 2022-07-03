package com.secondhand.ecommerce.models.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Column(name = "product_name")
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigInteger price;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_image",
            joinColumns = @JoinColumn(name = "product_id"))
    private List<String> productImages;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUsers appUsers;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories category;

    @Column(name = "is_published")
    private Boolean isPublished;

    @Column(name = "is_sold")
    private Boolean isSold;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return getId() != null && Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
