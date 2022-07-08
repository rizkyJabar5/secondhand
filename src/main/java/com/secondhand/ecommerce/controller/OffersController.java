package com.secondhand.ecommerce.controller;

import com.secondhand.ecommerce.models.dto.offers.OfferSave;
import com.secondhand.ecommerce.models.dto.offers.OfferUpdate;
import com.secondhand.ecommerce.service.OffersService;
import com.secondhand.ecommerce.utils.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/offers")
public class OffersController {

    private OffersService offersService;

    @PostMapping(value = "/buyer/add-offers")
    public ResponseEntity<?> addOffer(@ModelAttribute OfferSave request) {

        BaseResponse response = offersService.saveOffer(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/seller/status")
    public ResponseEntity<BaseResponse> updateStatus(@ModelAttribute OfferUpdate request) {
        BaseResponse response = offersService.updateOffer(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/seller/interested/{userId}")
    public ResponseEntity<?> interested(@PathVariable Long userId) {
        BaseResponse response = offersService.getOfferByUserId(userId);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

}
