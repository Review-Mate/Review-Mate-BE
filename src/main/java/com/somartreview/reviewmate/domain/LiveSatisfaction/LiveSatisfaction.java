package com.somartreview.reviewmate.domain.LiveSatisfaction;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.Customer.Customer;
import com.somartreview.reviewmate.domain.Review.ReviewProperty;
import com.somartreview.reviewmate.domain.TravelProduct.TravelProduct;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static javax.persistence.EnumType.*;

@Entity
@Getter
@NoArgsConstructor
public class LiveSatisfaction extends BaseEntity {

    private static final int LOWEST_RATING = 1;
    private static final int HIGHEST_RATING = 5;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "live_satisfaction_id")
    private Long id;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    @Enumerated(STRING)
    private ReviewProperty satisfiedReviewProperty;

    @Column(nullable = false)
    @Enumerated(STRING)
    private ReviewProperty dissatisfiedReviewProperty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "customer_partner_custom_id", nullable = false),
            @JoinColumn(name = "customer_partner_domain", nullable = false)
    })
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "travel_product_partner_custom_id", nullable = false),
            @JoinColumn(name = "travel_product_partner_domain", nullable = false)
    })
    private TravelProduct travelProduct;

    public LiveSatisfaction(Integer rating, ReviewProperty satisfiedReviewProperty, ReviewProperty dissatisfiedReviewProperty, Customer customer, TravelProduct travelProduct) {
        validateRating(rating);
        this.rating = rating;
        this.satisfiedReviewProperty = satisfiedReviewProperty;
        this.dissatisfiedReviewProperty = dissatisfiedReviewProperty;
        this.customer = customer;
        this.travelProduct = travelProduct;
    }

    private void validateRating(final Integer rating) {
        if (rating < LOWEST_RATING || rating > HIGHEST_RATING) {
            throw new DomainLogicException(ErrorCode.LIVE_SATISFACTION_RATING_ERROR);
        }
    }
}
