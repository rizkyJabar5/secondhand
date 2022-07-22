package com.secondhand.ecommerce.models.dto.offers;

import com.secondhand.ecommerce.models.entity.Offers;
import com.secondhand.ecommerce.models.enums.OfferStatus;
import com.secondhand.ecommerce.utils.DateUtilConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
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

    public OfferMapper offerToDto(Offers entity) {
        Date createdDate = entity.getCreatedDate();
        LocalDateTime date = DateUtilConverter.toLocalDate(createdDate);
        String formatDate = date.format(DateUtilConverter.formatter());

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
        dateCreated = formatDate;
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
