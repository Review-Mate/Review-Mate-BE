package com.somartreview.reviewmate.domain.TravelProduct;

import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.PartnerSeller.PartnerSeller;
import com.somartreview.reviewmate.dto.request.travelProduct.SingleTravelProductUpdateRequest;
import com.somartreview.reviewmate.dto.request.travelProduct.TravelProductUpdateRequest;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class SingleTravelProduct extends TravelProduct {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Builder
    public SingleTravelProduct(String partnerCustomId, String thumbnailUrl, String name, PartnerCompany partnerCompany, PartnerSeller partnerSeller, LocalDateTime startTime, LocalDateTime endTime, TravelProductCategory travelProductCategory) {
        super(partnerCustomId, thumbnailUrl, name, travelProductCategory, partnerCompany, partnerSeller);

        validateTime(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private void validateTime(final LocalDateTime startTime, final LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new DomainLogicException(ErrorCode.TRAVEL_PRODUCT_START_TIME_ERROR);
        }
    }

    public void update(SingleTravelProductUpdateRequest request, String thumbnailUrl) {
        super.update(new TravelProductUpdateRequest(request, thumbnailUrl));

        validateTime(request.getStartTime(), request.getEndTime());
        this.startTime = request.getStartTime();
        this.endTime = request.getEndTime();
    }
}
