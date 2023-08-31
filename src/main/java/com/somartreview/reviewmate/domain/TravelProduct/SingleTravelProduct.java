package com.somartreview.reviewmate.domain.TravelProduct;

import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.PartnerSeller.PartnerSeller;
import com.somartreview.reviewmate.dto.request.travelProduct.SingleTravelProductUpdateRequest;
import com.somartreview.reviewmate.dto.request.travelProduct.TravelProductUpdateRequest;
import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("SingleTravelProduct")
public class SingleTravelProduct extends TravelProduct {

    @Column(nullable = false, name = "category")
    @Enumerated(EnumType.STRING)
    private SingleTravelProductCategory singleTravelProductCategory;


    @Builder
    public SingleTravelProduct(String partnerCustomId, String thumbnailUrl, String name, PartnerCompany partnerCompany, PartnerSeller partnerSeller, LocalTime startTime, LocalTime endTime, SingleTravelProductCategory singleTravelProductCategory) {
        super(partnerCustomId, thumbnailUrl, name, partnerCompany, partnerSeller);

        this.singleTravelProductCategory = singleTravelProductCategory;
    }

    public void update(SingleTravelProductUpdateRequest request, String thumbnailUrl) {
        super.update(new TravelProductUpdateRequest(request, thumbnailUrl));

        this.singleTravelProductCategory = request.getSingleTravelProductCategory();
    }
}
