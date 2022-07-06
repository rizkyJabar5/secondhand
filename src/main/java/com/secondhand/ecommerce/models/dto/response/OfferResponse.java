package com.secondhand.ecommerce.models.dto.response;

import com.secondhand.ecommerce.models.entity.Offers;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OfferResponse {

    private Long userId;
    private Long productId;
    private Long offerId;
    private Long offerNegotiated;
    private String offerStatus;
    private LocalDateTime localDateTime;

    public OfferResponse(Offers offer) {
        this.userId = offer.getUserId().getUserId();
        this.productId = offer.getProductId().getId();
        this.offerId = offer.getOfferId();
        this.offerNegotiated = offer.getOfferNegotiated();
        this.offerStatus = offer.getOfferStatus();
        this.localDateTime = offer.getLocalDateTime();
    }
}