package com.secondhand.ecommerce.controller;

import com.secondhand.ecommerce.models.dto.offers.OfferSave;
import com.secondhand.ecommerce.models.dto.offers.OfferUpdate;
import com.secondhand.ecommerce.service.OffersService;
import com.secondhand.ecommerce.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/offers")
public class OffersController {

    private OffersService offersService;

    @Operation(summary = "Add offer from buyer to seller with default value = Waiting")
    @PostMapping(value = "/buyer/add-offers")
    public ResponseEntity<?> addOffer(@ModelAttribute OfferSave request) {

        BaseResponse response = offersService.saveOffer(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Update offer from seller with value = Accepted | Rejected")
    @PutMapping("/seller/status")
    public ResponseEntity<BaseResponse> updateStatus(@ModelAttribute OfferUpdate request) {
        BaseResponse response = offersService.updateOffer(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get offers for seller with id seller")
    @GetMapping("/seller/interested/{userId}")
    public ResponseEntity<?> interested(@PathVariable Long userId) {
        BaseResponse response = offersService.getOfferByUserId(userId);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(summary = "Seller confirm if offer is done, status product will be change isSold = true, with value by offerId")
    @PutMapping("/seller/update-product/{offerId}")
    public ResponseEntity<?> updateProductIsSold(@PathVariable Long offerId) {
        BaseResponse response = offersService.updateStatusProduct(offerId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
