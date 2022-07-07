package com.secondhand.ecommerce.models.dto.offers;

import lombok.Data;

@Data
public class OfferUpdate {
    private Long offerId;
    private String offerStatus;
}
