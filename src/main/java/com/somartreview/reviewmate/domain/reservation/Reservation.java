package com.somartreview.reviewmate.domain.reservation;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.customer.Customer;
import com.somartreview.reviewmate.domain.live.feedback.LiveFeedback;
import com.somartreview.reviewmate.domain.live.satisfaction.LiveSatisfaction;
import com.somartreview.reviewmate.domain.review.Review;
import com.somartreview.reviewmate.domain.product.TravelProduct;
import javax.persistence.*;

import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Reservation extends BaseEntity {

    private static final int MAX_PARTNER_CUSTOM_ID_LENGTH = 50;


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(nullable = false, unique = true, length = MAX_PARTNER_CUSTOM_ID_LENGTH)
    private String partnerCustomId;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_product_id", nullable = false)
    private TravelProduct travelProduct;

    @Builder
    public Reservation(String partnerCustomId, LocalDateTime startDateTime, LocalDateTime endDateTime, Customer customer, TravelProduct travelProduct) {
        validatePartnerCustomId(partnerCustomId);
        this.partnerCustomId = partnerCustomId;
        validateTime(startDateTime, endDateTime);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customer = customer;
        this.travelProduct = travelProduct;
    }

    private void validatePartnerCustomId(final String partnerCustomerId) {
        if (partnerCustomerId.isBlank() || partnerCustomerId.length() > MAX_PARTNER_CUSTOM_ID_LENGTH) {
            throw new DomainLogicException(ErrorCode.RESERVATION_PARTNER_CUSTOM_ID_ERROR);
        }
    }

    private void validateTime(final LocalDateTime startTime, final LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new DomainLogicException(ErrorCode.RESERVATION_FRONT_END_TIME_ERROR);
        }
    }
}
