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
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
public class SingleTravelProduct extends TravelProduct {

    private LocalTime startTime;

    private LocalTime endTime;

    @Builder
    public SingleTravelProduct(String partnerCustomId, String thumbnailUrl, String name, PartnerCompany partnerCompany, PartnerSeller partnerSeller, LocalTime startTime, LocalTime endTime, TravelProductCategory travelProductCategory) {
        super(partnerCustomId, thumbnailUrl, name, travelProductCategory, partnerCompany, partnerSeller);

        validateTime(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void update(SingleTravelProductUpdateRequest request, String thumbnailUrl) {
        super.update(new TravelProductUpdateRequest(request, thumbnailUrl));

        validateTime(request.getStartTime(), request.getEndTime());
        this.startTime = request.getStartTime();
        this.endTime = request.getEndTime();
    }

    private void validateTime(final LocalTime startTime, final LocalTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new DomainLogicException(ErrorCode.TRAVEL_PRODUCT_START_TIME_ERROR);
        }
    }
}
