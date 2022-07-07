package com.secondhand.ecommerce.models.dto.response;

import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.models.entity.Offers;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class OfferResponse {

    private Long productId;
    private Long offerId;
    private BigInteger offerNegotiated;
    private String offerStatus;
    private String buyer;
    private String timestamp;

    public OfferResponse(Offers offer) {
        this.productId = offer.getProduct().getId();
        this.offerId = offer.getId();
        this.offerNegotiated = offer.getOfferNegotiated();
        this.offerStatus = offer.getOfferStatus().name();
        this.buyer = offer.getCreatedBy();
        this.timestamp = offer.getCreatedDate().toString();
    }
}