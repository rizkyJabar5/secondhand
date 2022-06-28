package com.secondhand.ecommerce.repository;

import com.secondhand.ecommerce.models.entity.AppUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface AppUserRepository extends JpaRepository<AppUsers, Long> {

    Optional<AppUsers> findByEmail(String email);

    Optional<AppUsers> findByUserId(Long userId);

    Boolean existsByEmail(String email);


}
