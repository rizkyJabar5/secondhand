package com.secondhand.ecommerce.repository;

import com.secondhand.ecommerce.models.entity.Offers;
import com.secondhand.ecommerce.models.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OffersRepository extends JpaRepository<Offers, Long> {
//    @Query("delete from Offers where offerId=:offerId")
//    void deleteOffersById(Offers id);

    @Modifying
    @Query("update Offers set offerStatus ='Accepted' where offerId =:offerId")
    void statusAccepted(Long offerId);

    @Modifying
    @Query("update Offers set offerStatus ='Rejected' where offerId =:offerId")
    void statusRejected(Long offerId);
}
