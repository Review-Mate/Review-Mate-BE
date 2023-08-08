package com.somartreview.reviewmate.domain.LiveSatisfaction;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.Customer.Customer;
import com.somartreview.reviewmate.domain.Review.Property;
import com.somartreview.reviewmate.domain.TravelProduct.TravelProduct;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.*;

@Entity
@Getter
@NoArgsConstructor
public class LiveSatisfaction extends BaseEntity {

    private static final int LOWEST_RATING = 1;
    private static final int HIGHEST_RATING = 5;

    @Id @GeneratedValue
    @Column(name = "live_satisfaction_id")
    private Long id;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    @Enumerated(STRING)
    private Property satisfiedProperty;

    @Column(nullable = false)
    @Enumerated(STRING)
    private Property dissatisfiedProperty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_product_id", nullable = false)
    private TravelProduct travelProduct;

    public LiveSatisfaction(Integer rating, Property satisfiedProperty, Property dissatisfiedProperty, Customer customer, TravelProduct travelProduct) {
        this.rating = rating;
        this.satisfiedProperty = satisfiedProperty;
        this.dissatisfiedProperty = dissatisfiedProperty;
        this.customer = customer;
        this.travelProduct = travelProduct;
    }

    private void validateRating(final Integer rating) {
        if (rating < LOWEST_RATING || rating > HIGHEST_RATING) {
            throw new DomainLogicException(ErrorCode.LIVE_SATISFACTION_RATING_ERROR);
        }
    }
}
