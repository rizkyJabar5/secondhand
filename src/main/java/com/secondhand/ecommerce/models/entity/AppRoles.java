package com.secondhand.ecommerce.models.entity;

import com.secondhand.ecommerce.models.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_roles")
public class AppRoles extends BaseEntity {

    private ERole roleNames;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AppRoles roles = (AppRoles) o;
        return getId() != null && Objects.equals(getId(), roles.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
