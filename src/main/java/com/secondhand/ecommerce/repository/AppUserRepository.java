package com.secondhand.ecommerce.repository;

import com.secondhand.ecommerce.models.entity.AppUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface AppUserRepository extends JpaRepository<AppUsers, Long> {

    Optional<AppUsers> findByEmail(String email);

    Optional<AppUsers> findByUserId(Long userId);

    @Query(value =
            "select a from AppUsers a " +
                    "where a.userId = ?1 " +
                    "and a.address.city is not null " +
                    "and a.address.street is not null " +
                    "and a.phoneNumber is not null")
    Optional<AppUsers> checkProfileUser(Long userId);

    AppUsers findUserByUserId(Long userId);
}

