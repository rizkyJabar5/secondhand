package com.secondhand.ecommerce.repository;

import com.secondhand.ecommerce.models.entity.AppUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUsers, Long> {


}
