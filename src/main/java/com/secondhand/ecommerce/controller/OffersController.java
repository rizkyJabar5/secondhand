package com.secondhand.ecommerce.controller;

import com.secondhand.ecommerce.models.dto.offers.OfferSave;
import com.secondhand.ecommerce.models.entity.Offers;
import com.secondhand.ecommerce.models.enums.OfferStatus;
import com.secondhand.ecommerce.repository.OffersRepository;
import com.secondhand.ecommerce.service.AppUserService;
import com.secondhand.ecommerce.service.OffersService;
import com.secondhand.ecommerce.service.ProductService;
import com.secondhand.ecommerce.utils.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/offers")
public class OffersController {

    private OffersService offersService;
    private OffersRepository offersRepository;
    private ProductService productService;
    private AppUserService appUserService;

    @PostMapping(value = "/buyer/add-offers")
    public ResponseEntity<?> addOffer (
            @ModelAttribute OfferSave request
            ) {
        BaseResponse response = offersService.saveOffer(request);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PutMapping("/update/{offerId}/{status}")
    public ResponseEntity<?> updateStatusAccepted(@PathVariable ("offerId") Long offerId,
                                                  @PathVariable ("status") String status) {
        Offers offers = offersService.findByOfferId(offerId);
        if (Objects.equals(status, "accepted")) {
            offersService.updateStatusOffer(offers,offerId);
            offers.setStatusProcess(OfferStatus.Accepted);
            return new ResponseEntity<>("Status Accepted", HttpStatus.ACCEPTED);
        } else if (Objects.equals(status, "rejected")) {
            offersService.updateStatusOffer(offers,offerId);
            offers.setStatusProcess(OfferStatus.Rejected);
            return new ResponseEntity<>("Status Rejected", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Status not updated", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/seller/interested/{userId}")
    public ResponseEntity <?> interested(@PathVariable Long userId){
        BaseResponse response = offersService.getOfferByUserId(userId);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

}
