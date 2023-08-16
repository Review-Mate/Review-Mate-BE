package com.somartreview.reviewmate.domain.TravelProduct;

import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.PartnerSeller.PartnerSeller;
import com.somartreview.reviewmate.dto.request.travelProduct.SingleTravelProductUpdateRequest;
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Builder
    public SingleTravelProduct(String partnerSingleTravelProductId, String thumbnailUrl, String name, Float rating, PartnerCompany partnerCompany, PartnerSeller partnerSeller, LocalDateTime startTime, LocalDateTime endTime, Category category) {
        super(partnerSingleTravelProductId, thumbnailUrl, name, rating, partnerCompany, partnerSeller);
        validateTime(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
        this.category = category;
    }

    private void validateTime(final LocalDateTime startTime, final LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new DomainLogicException(ErrorCode.TRAVEL_PRODUCT_START_TIME_ERROR);
        }
    }

    public void update(SingleTravelProductUpdateRequest request, String thumbnailUrl, , PartnerCompany partnerCompany, PartnerSeller partnerSeller) {
        super.update(request.getPartnerSingleTravelProductId(), thumbnailUrl, request.getName(), partnerCompany, partnerSeller);
        validateTime(request.getStartTime(), request.getEndTime());
        this.startTime = request.getStartTime();
        this.endTime = request.getEndTime();
        this.category = request.getCategory();
    }
}
