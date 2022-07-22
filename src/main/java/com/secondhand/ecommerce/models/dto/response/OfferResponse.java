package com.secondhand.ecommerce.models.dto.response;

import com.secondhand.ecommerce.models.entity.Offers;
import com.secondhand.ecommerce.utils.DateUtilConverter;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class OfferResponse {

    private Long productId;
    private Long offerId;
    private BigInteger offerNegotiated;
    private String offerStatus;
    private String buyer;
    private String timestamp;

    public OfferResponse(Offers offer) {
        Date date = offer.getCreatedDate();
        LocalDateTime localDateTime = DateUtilConverter.toLocalDate(date);
        String formatDate = localDateTime.format(DateUtilConverter.formatter());

        this.productId = offer.getProduct().getId();
        this.offerId = offer.getId();
        this.offerNegotiated = offer.getOfferNegotiated();
        this.offerStatus = offer.getOfferStatus().name();
        this.buyer = offer.getCreatedBy();
        this.timestamp = formatDate;
    }
}