package com.secondhand.ecommerce.service;

import com.secondhand.ecommerce.models.dto.offers.OfferSave;
import com.secondhand.ecommerce.models.entity.Offers;
import com.secondhand.ecommerce.utils.BaseResponse;

public interface OffersService {
    BaseResponse saveOffer(OfferSave request);
    BaseResponse updateOffer(Long offerId);
    BaseResponse getOfferByUserId(Long userId);
    Offers findByOfferId(Long offerId);
    BaseResponse updateStatusOffer(Offers offers, Long offerId);
}
