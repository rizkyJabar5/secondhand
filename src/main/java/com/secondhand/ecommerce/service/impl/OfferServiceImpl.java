package com.secondhand.ecommerce.service.impl;

import com.secondhand.ecommerce.exceptions.AppBaseException;
import com.secondhand.ecommerce.models.dto.offers.OfferMapper;
import com.secondhand.ecommerce.models.dto.offers.OfferSave;
import com.secondhand.ecommerce.models.dto.offers.OfferUpdate;
import com.secondhand.ecommerce.models.dto.users.AppUserBuilder;
import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.models.entity.Offers;
import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.models.enums.OfferStatus;
import com.secondhand.ecommerce.models.enums.OperationStatus;
import com.secondhand.ecommerce.repository.OffersRepository;
import com.secondhand.ecommerce.security.SecurityUtils;
import com.secondhand.ecommerce.service.AppUserService;
import com.secondhand.ecommerce.service.OffersService;
import com.secondhand.ecommerce.service.ProductService;
import com.secondhand.ecommerce.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.secondhand.ecommerce.utils.SecondHandConst.EMAIL_NOT_FOUND_MSG;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OffersService {

    private final OffersRepository offersRepository;
    private final AppUserService userService;
    private final ProductService productService;
    private final OfferMapper offerMapper;

    @Override
    public BaseResponse saveOffer(OfferSave request) {

        final AppUserBuilder userDetails = SecurityUtils.getAuthenticatedUserDetails();
        boolean authenticated = SecurityUtils.isAuthenticated();
        AppUsers users = userService.findUserByEmail(Objects.requireNonNull(userDetails).getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(EMAIL_NOT_FOUND_MSG, userDetails.getEmail())));

        Product product = productService.getProductById(request.getProductId());
        Offers offers = new Offers();

        Long buyer = userDetails.getUserId();
        Long seller = product.getAppUsers().getUserId();

//        BigInteger priceNegotiatedMax = product.getPrice();
//        priceNegotiatedMax = priceNegotiatedMax.divide(100).multiply(100);
        if (authenticated) {
            offers.setUser(users);
            offers.setCreatedBy(users.getEmail());
            offers.setProduct(product);
            offers.setOfferNegotiated(request.getPriceNegotiated());

            boolean present = offersRepository.findByUserIdAndProduct(buyer, product.getId()).isPresent();

            if (Objects.deepEquals(buyer, seller)) {
                return new BaseResponse(HttpStatus.BAD_REQUEST,
                        "You can't bid on your own product",
                        OperationStatus.FAILURE);
            } else if (present) {
                return new BaseResponse(HttpStatus.BAD_REQUEST,
                        "You have made an offer, please wait for confirmation from the seller",
                        OperationStatus.FAILURE);
            }

            offersRepository.save(offers);
        }

        return new BaseResponse(HttpStatus.OK,
                "Your bid price has been successfully sent to the seller",
                offerMapper.offerToDto(offers),
                OperationStatus.SUCCESS);
    }

    @Override
    public BaseResponse updateOffer(OfferUpdate request) {

        boolean authenticated = SecurityUtils.isAuthenticated();
        AppUserBuilder userDetails = SecurityUtils.getAuthenticatedUserDetails();

        Offers updatedOffers = offersRepository.findById(request.getOfferId())
                .orElseThrow(() -> new AppBaseException("Offer not found"));

        Long sellerId = updatedOffers.getProduct().getAppUsers().getUserId();
        Long sellerLogin = Objects.requireNonNull(userDetails).getUserId();

        if (authenticated && Objects.equals(sellerId, sellerLogin)) {
            if (request.getOfferStatus().equals(OfferStatus.Accepted)) {
                updatedOffers.setOfferStatus(OfferStatus.Accepted);

                offersRepository.save(updatedOffers);

            } else if (request.getOfferStatus().equals(OfferStatus.Rejected)) {
                updatedOffers.setOfferStatus(OfferStatus.Rejected);
                offersRepository.save(updatedOffers);

                return new BaseResponse(HttpStatus.OK,
                        "Offer has been rejected.",
                        updatedOffers.getOfferStatus(),
                        OperationStatus.SUCCESS);
            }
        } else {
            return new BaseResponse(HttpStatus.BAD_REQUEST,
                    "You must be authenticated or you must be seller to accept offer.",
                    OperationStatus.SUCCESS);
        }

        return new BaseResponse(HttpStatus.OK,
                "Offer has been accepted, please call your buyer.",
                updatedOffers.getOfferStatus(),
                OperationStatus.SUCCESS);
    }

    @Override
    public BaseResponse getOfferByUserId(Long userId) {
        List<OfferMapper> productOffer = offersRepository.findByUserId(userId)
                .stream()
                .map(offerMapper::offerToDto)
                .collect(Collectors.toList());

        if (productOffer.isEmpty()) {
            return new BaseResponse(HttpStatus.NOT_FOUND,
                    "Offers not found on user: " + userId,
                    OperationStatus.NOT_FOUND);
        }

        return new BaseResponse(HttpStatus.OK,
                "Offer found " + productOffer.get(0).getBuyer(),
                productOffer,
                OperationStatus.FOUND);
    }

}
