package com.secondhand.ecommerce.models.dto.offers;

import com.secondhand.ecommerce.models.entity.Offers;
import com.secondhand.ecommerce.models.enums.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class OfferMapper {
    private Long offerId;
    private Long productId;
    private String productName;
    private BigInteger price;
    private Long categoryId;
    private BigInteger priceOffer;
    private String buyer;
    private String cityBuyer;
    private String avatarBuyer;
    private String seller;
    private String citySeller;
    private String avatarSeller;
    private String dateCreated;
    private OfferStatus statusOffer;
    private List<String> productImages;

    public OfferMapper(String productName,
                       BigInteger price,
                       BigInteger priceOffer,
                       String cityBuyer,
                       String dateCreated) {
        this.productName = productName;
        this.price = price;
        this.priceOffer = priceOffer;
        this.cityBuyer = cityBuyer;
        this.dateCreated = dateCreated;
    }

    public OfferMapper offerToDto(Offers entity) {
        offerId = entity.getId();
        productId = entity.getProduct().getId();
        productName = entity.getProduct().getProductName();
        price = entity.getProduct().getPrice();
        categoryId = entity.getProduct().getCategory().getId();
        priceOffer = entity.getOfferNegotiated();
        buyer = entity.getUser().getFullName();
        cityBuyer = entity.getUser().getAddress().getCity();
        avatarBuyer = entity.getUser().getImageUrl();
        seller = entity.getProduct().getCreatedBy();
        citySeller = entity.getProduct().getAppUsers().getAddress().getCity();
        avatarSeller = entity.getProduct().getAppUsers().getImageUrl();
        dateCreated = entity.getCreatedDate().toString();
        statusOffer = entity.getOfferStatus();
        productImages = entity.getProduct().getProductImages();
        return new OfferMapper(offerId,
                productId,
                productName,
                price,
                categoryId,
                priceOffer,
                buyer,
                cityBuyer,
                avatarBuyer,
                seller,
                citySeller,
                avatarSeller,
                dateCreated,
                statusOffer,
                productImages);
    }
}
