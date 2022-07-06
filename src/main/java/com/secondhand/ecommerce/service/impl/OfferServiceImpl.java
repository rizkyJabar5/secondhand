package com.secondhand.ecommerce.service.impl;

import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.models.entity.Offers;
import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.repository.OffersRepository;
import com.secondhand.ecommerce.service.AppUserService;
import com.secondhand.ecommerce.service.OffersService;
import com.secondhand.ecommerce.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OfferServiceImpl implements OffersService {

    private OffersRepository offersRepository;
    private AppUserService appUserService;
    private ProductService productService;

//    @Override
//    public void deleteOffersById(Offers id) {
//        offersRepository.deleteById(id.getOfferId());
//    }

    @Override
    public void saveOffer(Long offerId, Long userId, Long productId, Long offerNegotiated, String offerStatus, LocalDateTime dateTime) {
        Offers offer = new Offers();
        AppUsers users = appUserService.findByUserId(userId);
        Product product = productService.getProductById(productId);
        offer.setProductId(product);
        offer.setOfferId(offerId);
        offer.setUserId(users);
        offer.setOfferNegotiated(offerNegotiated);
        offer.setOfferStatus(offerStatus);
        offer.setLocalDateTime(dateTime);
        offersRepository.save(offer);
    }

    @Override
    public void acceptedStatus(Long offerId) {
        offersRepository.statusAccepted(offerId);
    }

    @Override
    public Optional<Offers> findOfferById(Long offerId) {
        return offersRepository.findById(offerId);
    }

    @Override
    public void rejectedStatus(Long offerId) {
        offersRepository.statusRejected(offerId);
    }


}
