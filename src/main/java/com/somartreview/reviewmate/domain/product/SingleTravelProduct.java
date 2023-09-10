package com.somartreview.reviewmate.domain.product;

import com.somartreview.reviewmate.domain.partner.company.PartnerCompany;
import com.somartreview.reviewmate.domain.partner.seller.PartnerSeller;
import com.somartreview.reviewmate.dto.product.SingleTravelProductUpdateRequest;
import com.somartreview.reviewmate.dto.product.TravelProductUpdateRequest;
import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("SingleTravelProduct")
public class SingleTravelProduct extends TravelProduct {

    @Column(nullable = false, name = "category")
    @Enumerated(EnumType.STRING)
    private SingleTravelProductCategory singleTravelProductCategory;


    @Builder
    public SingleTravelProduct(String partnerCustomId, String thumbnailUrl, String name, PartnerCompany partnerCompany, PartnerSeller partnerSeller, SingleTravelProductCategory singleTravelProductCategory) {
        super(partnerCustomId, thumbnailUrl, name, partnerCompany, partnerSeller);

        this.singleTravelProductCategory = singleTravelProductCategory;
    }

    public void update(SingleTravelProductUpdateRequest request, String thumbnailUrl) {
        super.update(new TravelProductUpdateRequest(request, thumbnailUrl));

        this.singleTravelProductCategory = request.getSingleTravelProductCategory();
    }
}
