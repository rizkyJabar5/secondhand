package com.secondhand.ecommerce.models.dto.offers;

import lombok.Data;

import java.math.BigInteger;

@Data
public class OfferSave {

    private Long productId;
    private BigInteger priceNegotiated;

}
