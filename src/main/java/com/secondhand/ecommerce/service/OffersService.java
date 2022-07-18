package com.secondhand.ecommerce.service;

import com.secondhand.ecommerce.models.dto.offers.OfferSave;
import com.secondhand.ecommerce.models.dto.offers.OfferUpdate;
import com.secondhand.ecommerce.models.entity.Offers;
import com.secondhand.ecommerce.utils.BaseResponse;

public interface OffersService {
    BaseResponse saveOffer(OfferSave request);

    BaseResponse updateOffer(OfferUpdate request);

    BaseResponse getOfferBySellerId(Long userId);

    BaseResponse getOfferSellerByProductId(Long productId);

    BaseResponse updateStatusProduct(Long offerId);
//    BaseResponse getStatusOffer(Long userId, Long offerId);

    BaseResponse loadOfferById(Long offerId);

    Offers getOfferById(Long offerId);
}
