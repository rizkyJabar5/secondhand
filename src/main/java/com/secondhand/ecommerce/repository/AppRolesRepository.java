package com.secondhand.ecommerce.repository;

import com.secondhand.ecommerce.models.entity.AppRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRolesRepository extends JpaRepository<AppRoles, Long> {
}
