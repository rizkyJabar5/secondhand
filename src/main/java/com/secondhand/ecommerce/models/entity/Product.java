package com.secondhand.ecommerce.models.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Null;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product extends AbstractEntity {
    private String name;
    private Long price;
    private String location;
    private String image;
    private String category;
}
