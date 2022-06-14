package com.secondhand.ecommerce.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {

    private String street;
    private String city;

}
