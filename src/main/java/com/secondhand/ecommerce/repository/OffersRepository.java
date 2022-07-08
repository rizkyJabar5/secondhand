package com.secondhand.ecommerce.repository;

import com.secondhand.ecommerce.models.entity.Offers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface OffersRepository extends JpaRepository<Offers, Long> {

    @Query("select o from Offers o where o.user.userId=?1")
    List<Offers> findByUserId(Long user);

    @Query("select o from Offers o where o.user.userId=?1 and o.product.id=?2  ")
    Optional<Offers> findByUserIdAndProduct(Long user, Long productId);
}
