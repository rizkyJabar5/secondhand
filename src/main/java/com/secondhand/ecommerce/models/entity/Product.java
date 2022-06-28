package com.secondhand.ecommerce.models.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;
    private String description;
    private Long price;
    private String category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUsers userId;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public Long getPrice() {return price;}
    public void setPrice(Long price) {this.price = price;}
    public void setCategory(String category){this.category = category;}
    public String getCategory(){return category;}
    public void setUserId(AppUsers users) {this.userId = userId;}
    public void setProductId(Long productId) {this.productId = productId;}
    public Long getProductId(){return productId;}
}
