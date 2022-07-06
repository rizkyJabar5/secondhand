package com.secondhand.ecommerce.service;

import com.secondhand.ecommerce.models.entity.Offers;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OffersService {
    //    void deleteOffersById(Offers id);
    void saveOffer(Long offerId, Long userId, Long productId, Long offerNegotiated, String offerStatus, LocalDateTime dateTime);
    void acceptedStatus(Long offerId);
    Optional<Offers> findOfferById(Long offerId);
    void rejectedStatus(Long offerId);
}
