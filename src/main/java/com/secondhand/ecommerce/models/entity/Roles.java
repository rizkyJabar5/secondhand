package com.secondhand.ecommerce.models.entity;

import com.secondhand.ecommerce.models.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "roles")
public class Roles extends AbstractEntity{

    private ERole roleNames;

}
