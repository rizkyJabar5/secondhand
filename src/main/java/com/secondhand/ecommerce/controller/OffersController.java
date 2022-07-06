package com.secondhand.ecommerce.controller;

import com.secondhand.ecommerce.models.dto.response.OfferResponse;
import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.models.entity.Offers;
import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.repository.OffersRepository;
import com.secondhand.ecommerce.service.AppUserService;
import com.secondhand.ecommerce.service.OffersService;
import com.secondhand.ecommerce.service.ProductService;
import com.sun.org.apache.xerces.internal.util.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/offers")
public class OffersController {

    private OffersService offersService;
    private OffersRepository offersRepository;
    private ProductService productService;
    private AppUserService appUserService;

    @PostMapping(value = "/buyer/add-offers/{userId}/{productId}")
    public ResponseEntity<OfferResponse> addOffer (
            @PathVariable("userId") Long userId,
            @PathVariable("productId") Long productId,
            @RequestParam("offerId") Long offerId,
            @RequestParam("offerNegotiated") Long offerNegotiated,
            @RequestParam(defaultValue = "Waiting", required = false)String offerStatus
    ) {

        AppUsers users = appUserService.findByUserId(userId);
        Product product = productService.getProductById(productId);
        Offers offer = new Offers();
        offer.setUserId(users);
        offer.setProductId(product);
        offer.setOfferId(offerId);
        offer.setOfferNegotiated(offerNegotiated);
        offer.setOfferStatus(offerStatus);
        LocalDateTime dateTime = LocalDateTime.now();
        offer.setLocalDateTime(dateTime);
        offersService.saveOffer(offerId, userId, productId, offerNegotiated, offerStatus, dateTime);
        return new ResponseEntity(new OfferResponse(offer), HttpStatus.OK);
    }

    @PutMapping("/accepted-status/{offerId}")
    public ResponseEntity<OfferResponse> accStatus(
            @PathVariable("offerId") Long offerId){
        offersService.acceptedStatus(offerId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/rejected-status/{offerId}")
    public ResponseEntity<OfferResponse> rejectStatus(
            @PathVariable("offerId") Long offerId){
        offersService.rejectedStatus(offerId);
        return new ResponseEntity(HttpStatus.OK);
    }

//    @DeleteMapping(value = "/delete-offers")
//    public String deleteOffers(Offers offerId){
//        offersService.deleteOffersById(offerId);
//        return "Success delete offer ";
//    }

}
