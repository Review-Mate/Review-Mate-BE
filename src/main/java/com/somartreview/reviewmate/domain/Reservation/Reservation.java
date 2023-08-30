package com.somartreview.reviewmate.domain.Reservation;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.Customer.Customer;
import com.somartreview.reviewmate.domain.LiveFeedback.LiveFeedback;
import com.somartreview.reviewmate.domain.LiveSatisfaction.LiveSatisfaction;
import com.somartreview.reviewmate.domain.Review.Review;
import com.somartreview.reviewmate.domain.TravelProduct.TravelProduct;
import javax.persistence.*;

import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private LocalDate startDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_product_id", nullable = false)
    private TravelProduct travelProduct;

    @OneToOne(mappedBy = "reservation")
    private Review review;

    @OneToOne(mappedBy = "reservation")
    private LiveSatisfaction liveSatisfaction;

    @OneToOne(mappedBy = "reservation")
    private LiveFeedback liveFeedback;


    @Builder
    public Reservation(String partnerCustomId, LocalDate startDate, Customer customer, TravelProduct travelProduct) {
        validatePartnerCustomId(partnerCustomId);
        this.partnerCustomId = partnerCustomId;
        this.startDate = startDate;
        this.customer = customer;
        this.travelProduct = travelProduct;
    }

    private void validatePartnerCustomId(final String partnerCustomerId) {
        if (partnerCustomerId.isBlank() || partnerCustomerId.length() > MAX_PARTNER_CUSTOM_ID_LENGTH) {
            throw new DomainLogicException(ErrorCode.RESERVATION_PARTNER_CUSTOM_ID_ERROR);
        }
    }
}
