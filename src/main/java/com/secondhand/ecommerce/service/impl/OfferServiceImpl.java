package com.secondhand.ecommerce.service.impl;

import com.secondhand.ecommerce.models.dto.offers.OfferMapper;
import com.secondhand.ecommerce.models.dto.offers.OfferSave;
import com.secondhand.ecommerce.models.dto.response.OfferResponse;
import com.secondhand.ecommerce.models.dto.users.AppUserBuilder;
import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.models.entity.Offers;
import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.models.enums.OperationStatus;
import com.secondhand.ecommerce.repository.OffersRepository;
import com.secondhand.ecommerce.security.SecurityUtils;
import com.secondhand.ecommerce.service.AppUserService;
import com.secondhand.ecommerce.service.OffersService;
import com.secondhand.ecommerce.service.ProductService;
import com.secondhand.ecommerce.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OffersService {

    private final OffersRepository offersRepository;
    private final AppUserService appUserService;
    private final ProductService productService;
    private final OfferMapper offerMapper;

    @Override
    public BaseResponse saveOffer(OfferSave request) {
        final AppUserBuilder userDetails = SecurityUtils.getAuthenticatedUserDetails();
        AppUsers users = appUserService.findByUserId(userDetails.getUserId());
        Product product = productService.getProductById(request.getProductId());
        Offers offers = new Offers();
        offers.setUser(users);
        offers.setCreatedBy(users.getEmail());
        offers.setProduct(product);
        offers.setOfferNegotiated(request.getPriceNegotiated());
        offersRepository.save(offers);

        return new BaseResponse(HttpStatus.OK,
                "Your bid price has been successfully sent to the seller",
                new OfferResponse(offers),
                OperationStatus.SUCCESS);
    }

    @Override
    public BaseResponse updateOffer(Long offerId) {
        return null;
    }

//    @Override
//    public void updatePrice(Long offerId) {
//        Offers update = offersRepository.findById(offerId)
//                .orElseThrow(() -> new AppBaseException("Offers id not found"));
//        update.setOfferNegotiated();
//    }

//    @Override
//    public BaseResponse updateOffer(Long offerId) {
//        boolean authenticated = SecurityUtils.isAuthenticated();
//
//        Offers updatedOffers = offersRepository.findById(update.getOfferId())
//                .orElseThrow(() -> new AppBaseException("Offer not found"));
//        if (authenticated){
//            updatedOffers.setOfferStatus(update.getOfferStatus());
//        }
//        Offers offers = offersRepository.findById(update.getOfferId()).get();
//        offers.getOfferStatus();
//        offersRepository.save(offers);
//
//        return new BaseResponse(HttpStatus.OK,
//                "Your bid price has been successfully sent to the seller",
//                new OfferResponse(offers),
//                OperationStatus.SUCCESS);
//    }

    @Override
    public BaseResponse getOfferByUserId(Long userId) {
        List<OfferMapper> productOffer = offersRepository.findByUserId(userId)
                .stream()
                .map(offerMapper::offerToDto)
                .collect(Collectors.toList());

        if (productOffer.isEmpty()){
            return new BaseResponse(HttpStatus.NOT_FOUND,
                    "Offers not found on user: " +userId,
                    OperationStatus.NOT_FOUND);
        }

        return new BaseResponse(HttpStatus.OK,
                "Offer found " + productOffer.get(0).getAddedBy(),
                productOffer,
                OperationStatus.FOUND);
    }

    @Override
    public Offers findByOfferId(Long offerId) {
        return offersRepository.findByOfferId(offerId);
    }

    @Override
    public BaseResponse updateStatusOffer(Offers offers, Long offerId) {
        Offers updateOffers = offersRepository.findByOfferId(offers.getId());
        updateOffers.setOfferStatus(offers.getOfferStatus());
        OfferResponse response = new OfferResponse(offers.getOfferStatus().toString());
        return new BaseResponse(HttpStatus.OK,
                "Success to update status",
                response,
                OperationStatus.SUCCESS);
    }
}
