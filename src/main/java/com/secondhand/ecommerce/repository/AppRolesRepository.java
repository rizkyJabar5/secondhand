package com.secondhand.ecommerce.repository;

import com.secondhand.ecommerce.models.entity.AppRoles;
import com.secondhand.ecommerce.models.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppRolesRepository extends JpaRepository<AppRoles, Long> {
    Optional<AppRoles> findByRoleNames(ERole roleName);
}
