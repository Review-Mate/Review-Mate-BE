package com.somartreview.reviewmate.domain.liveSatisfaction;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.reservation.Reservation;
import com.somartreview.reviewmate.domain.review.ReviewProperty;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public LiveSatisfaction(Integer rating, ReviewProperty satisfiedReviewProperty, ReviewProperty dissatisfiedReviewProperty, Reservation reservation) {
        validateRating(rating);
        this.rating = rating;
        this.satisfiedReviewProperty = satisfiedReviewProperty;
        this.dissatisfiedReviewProperty = dissatisfiedReviewProperty;
        this.reservation = reservation;
    }

    private void validateRating(final Integer rating) {
        if (rating < LOWEST_RATING || rating > HIGHEST_RATING) {
            throw new DomainLogicException(ErrorCode.LIVE_SATISFACTION_RATING_ERROR);
        }
    }
}
