package com.secondhand.ecommerce.controller;

import com.secondhand.ecommerce.models.dto.offers.OfferSave;
import com.secondhand.ecommerce.models.dto.offers.OfferUpdate;
import com.secondhand.ecommerce.models.dto.response.OfferResponse;
import com.secondhand.ecommerce.models.entity.AppUsers;
import com.secondhand.ecommerce.models.entity.Offers;
import com.secondhand.ecommerce.models.entity.Product;
import com.secondhand.ecommerce.repository.OffersRepository;
import com.secondhand.ecommerce.service.AppUserService;
import com.secondhand.ecommerce.service.OffersService;
import com.secondhand.ecommerce.service.ProductService;
import com.secondhand.ecommerce.utils.BaseResponse;
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

    @PostMapping(value = "/buyer/add-offers")
    public ResponseEntity<?> addOffer (
            @ModelAttribute OfferSave request
            ) {
        BaseResponse response = offersService.saveOffer(request);
        return new ResponseEntity(response, HttpStatus.OK);
    }


    @PutMapping("/status")
    public ResponseEntity<BaseResponse> updateStatus(
            @ModelAttribute OfferUpdate request) {
        BaseResponse response = offersService.updateOffer(request);
        return new ResponseEntity(response,HttpStatus.OK);
    }
        //        offersService.acceptedStatus(offerId);
//        offersService.updatePrice(offerId);
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    @PutMapping("/rejected-status/{offerId}")
//    public ResponseEntity<OfferResponse> rejectStatus(@PathVariable("offerId") Long offerId){
//        offersService.rejectedStatus(offerId);
//        return new ResponseEntity(HttpStatus.OK);
//    }

//    @GetMapping("/historySeller")

    @GetMapping("/history-buyer/{userId}")
    public ResponseEntity <?> historySeller(@PathVariable Long userId){
        BaseResponse response = offersService.getOfferByUserId(userId);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }




}
