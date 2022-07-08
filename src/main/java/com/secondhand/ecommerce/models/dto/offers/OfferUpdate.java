package com.secondhand.ecommerce.models.dto.offers;

import com.secondhand.ecommerce.models.enums.OfferStatus;
import lombok.Data;

@Data
public class OfferUpdate {

    private Long offerId;
    private OfferStatus offerStatus;

}
