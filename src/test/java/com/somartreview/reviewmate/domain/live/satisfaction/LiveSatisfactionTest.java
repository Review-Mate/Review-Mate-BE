package com.somartreview.reviewmate.domain.live.satisfaction;

import com.somartreview.reviewmate.domain.reservation.Reservation;
import com.somartreview.reviewmate.domain.review.ReviewProperty;
import com.somartreview.reviewmate.exception.DomainLogicException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.somartreview.reviewmate.exception.ErrorCode.LIVE_SATISFACTION_RATING_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LiveSatisfactionTest {

    private LiveSatisfaction liveSatisfaction;

    @BeforeEach
    void setup() {
        liveSatisfaction = LiveSatisfaction.builder()
                .rating(5)
                .satisfiedReviewProperty(ReviewProperty.CLEANNESS)
                .dissatisfiedReviewProperty(ReviewProperty.CLEANNESS)
                .reservation(new Reservation())
                .build();
    }

    @Test
    void 만족도를_생성한다() {
        // when
        LiveSatisfaction liveSatisfaction = new LiveSatisfaction(5, ReviewProperty.CLEANNESS, ReviewProperty.CLEANNESS, new Reservation());

        // then
        assertThat(liveSatisfaction)
                .extracting("rating", "satisfiedReviewProperty", "dissatisfiedReviewProperty")
                .containsExactly(5, ReviewProperty.CLEANNESS, ReviewProperty.CLEANNESS);
        assertThat(liveSatisfaction.getReservation()).isInstanceOf(Reservation.class);
    }

    @Test
    void 만족도_점수는_1점보다_낮으면_안된다() {
        // given
        int rating = 0;
        Reservation mockReservation = new Reservation();

        // when & then
        assertThatThrownBy(() -> new LiveSatisfaction(rating, ReviewProperty.CLEANNESS, ReviewProperty.CLEANNESS, mockReservation))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(LIVE_SATISFACTION_RATING_ERROR.getMessage());
    }

    @Test
    void 만족도_점수는_5점보다_높으면_안된다() {
        // given
        int rating = 6;
        Reservation mockReservation = new Reservation();

        // when & then
        assertThatThrownBy(() -> new LiveSatisfaction(rating, ReviewProperty.CLEANNESS, ReviewProperty.CLEANNESS, mockReservation))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(LIVE_SATISFACTION_RATING_ERROR.getMessage());
    }
}