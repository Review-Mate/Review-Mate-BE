package com.somartreview.reviewmate.domain.TravelProduct;

import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.PartnerSeller.PartnerSeller;
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

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Builder
    public SingleTravelProduct(String clientSideId, String thumbnailUrl, String name, Float rating, PartnerCompany partnerCompany, PartnerSeller partnerSeller, LocalDateTime startTime, LocalDateTime endTime, Category category) {
        super(clientSideId, thumbnailUrl, name, rating, partnerCompany, partnerSeller);
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
}
