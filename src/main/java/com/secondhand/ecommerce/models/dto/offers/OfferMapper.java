package com.secondhand.ecommerce.models.dto.offers;

import com.secondhand.ecommerce.models.entity.Offers;
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
    private String buyer;
    private String seller;
    private List<String> productImages;

    public OfferMapper offerToDto(Offers entity) {
        offerId = entity.getId();
        productId = entity.getProduct().getId();
        productName = entity.getProduct().getProductName();
        price = entity.getProduct().getPrice();
        buyer = entity.getCreatedBy();
        seller = entity.getProduct().getCreatedBy();
        productImages = entity.getProduct().getProductImages();
        return new OfferMapper(offerId,
                productId,
                productName,
                price,
                buyer,
                seller,
                productImages);
    }
}
