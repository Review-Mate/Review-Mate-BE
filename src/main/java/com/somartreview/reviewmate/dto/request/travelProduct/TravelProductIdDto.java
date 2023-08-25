package com.somartreview.reviewmate.dto.request.travelProduct;

import com.somartreview.reviewmate.domain.TravelProduct.TravelProductId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TravelProductIdDto {

    String partnerCustomId;

    String partnerDomain;

    public TravelProductId toEntity() {
        return TravelProductId.builder()
                .partnerCustomId(partnerCustomId)
                .partnerDomain(partnerDomain)
                .build();
    }
}
