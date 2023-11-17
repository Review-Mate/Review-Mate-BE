package com.somartreview.reviewmate.domain.review;


import com.somartreview.reviewmate.domain.reservation.Reservation;
import com.somartreview.reviewmate.domain.review.image.ReviewImage;
import com.somartreview.reviewmate.domain.review.tag.ReviewTag;
import com.somartreview.reviewmate.dto.review.ReviewUpdateRequest;
import com.somartreview.reviewmate.exception.DomainLogicException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.somartreview.reviewmate.exception.ErrorCode.*;
import static com.somartreview.reviewmate.exception.ErrorCode.REVIEW_CONTENT_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReviewTest {

    private Review review;

    @BeforeEach
    void setup() {
        review = Review.builder()
                .rating(5)
                .title("제목")
                .content("내용")
                .reservation(new Reservation())
                .build();
    }


    @Test
    void 리뷰의_정보를_수정한다() {
        // given
        ReviewUpdateRequest reviewUpdateRequest = new ReviewUpdateRequest(1, "변경 제목", "변경 내용");

        // when
        review.updateReview(reviewUpdateRequest);

        // then
        assertThat(review)
                .extracting("rating", "title", "content")
                .containsExactly(1, "변경 제목", "변경 내용");
    }

    @Test
    void 리뷰_점수는_1점_보다_낮으면_안된다() {
        // given
        int rating = 0;
        Reservation mockReservation = new Reservation();

        // when & then
        assertThatThrownBy(() -> new Review(rating, "제목", "내용", mockReservation))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(REVIEW_RATING_ERROR.getMessage());
    }

    @Test
    void 리뷰_점수는_5점_보다_낮으면_안된다() {
        // given
        int rating = 6;
        Reservation mockReservation = new Reservation();

        // when & then
        assertThatThrownBy(() -> new Review(rating, "제목", "내용", mockReservation))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(REVIEW_RATING_ERROR.getMessage());
    }

    @Test
    void 리뷰_제목은_공백이면_안된다() {
        // given
        String title = " ";
        Reservation mockReservation = new Reservation();

        // when & then
        assertThatThrownBy(() -> new Review(5, title, "내용", mockReservation))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(REVIEW_TITLE_ERROR.getMessage());
    }

    @Test
    void 리뷰_제목은_255자_보다_길면_안된다() {
        // given
        String title = "a".repeat(256);
        Reservation mockReservation = new Reservation();

        // when & then
        assertThatThrownBy(() -> new Review(5, title, "내용", mockReservation))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(REVIEW_TITLE_ERROR.getMessage());
    }

    @Test
    void 리뷰_내용은_공백이면_안된다() {
        // given
        String content = " ";
        Reservation mockReservation = new Reservation();

        // when & then
        assertThatThrownBy(() -> new Review(5, "제목", content, mockReservation))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(REVIEW_CONTENT_ERROR.getMessage());
    }

    @Test
    void 리뷰_내용은_255자_보다_길면_안된다() {
        // given
        String content = "a".repeat(256);
        Reservation mockReservation = new Reservation();

        // when & then
        assertThatThrownBy(() -> new Review(5, "제목", content, mockReservation))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(REVIEW_CONTENT_ERROR.getMessage());
    }

    @Test
    void 리뷰에_긍정_리뷰태그를_추가한다() {
        // given
        Review mockReview = new Review();
        ReviewTag reviewTag = new ReviewTag(ReviewProperty.LOCATION, "키워드", ReviewPolarity.POSITIVE, 0, 1, mockReview);

        // when
        review.addReviewTag(reviewTag);

        // then
        assertThat(review.getReviewTags()).hasSize(1);
        assertThat(review.getPolarity()).isEqualTo(ReviewPolarity.POSITIVE);
    }

    @Test
    void 리뷰에_부정_리뷰태그를_추가한다() {
        // given
        Review mockReview = new Review();
        ReviewTag reviewTag = new ReviewTag(ReviewProperty.LOCATION, "키워드", ReviewPolarity.NEGATIVE, 0, 1, mockReview);


        // when
        review.addReviewTag(reviewTag);

        // then
        assertThat(review.getReviewTags()).hasSize(1);
        assertThat(review.getPolarity()).isEqualTo(ReviewPolarity.NEGATIVE);
    }

    @Test
    void 리뷰에_중립_리뷰태그를_추가한다() {
        // given
        Review mockReview1 = new Review();
        ReviewTag positiveReviewTag = new ReviewTag(ReviewProperty.LOCATION, "키워드", ReviewPolarity.POSITIVE, 0, 1, mockReview1);
        Review mockReview2 = new Review();
        ReviewTag negativeReviewTag = new ReviewTag(ReviewProperty.LOCATION, "키워드", ReviewPolarity.NEGATIVE, 0, 1, mockReview2);
        List<ReviewTag> reviewTags = new ArrayList<>(List.of(positiveReviewTag, negativeReviewTag));

        // when
        review.addReviewTag(positiveReviewTag);
        review.addReviewTag(negativeReviewTag);


        // then
        assertThat(review.getReviewTags()).hasSize(2);
        assertThat(review.getPolarity()).isEqualTo(ReviewPolarity.NEUTRAL);
    }

    @Test
    void 리뷰에_리뷰태그를_초기화한다() {
        // then
        review.clearReviewTags();

        // then
        assertThat(review.getReviewTags()).isEmpty();
        assertThat(review.getPolarity()).isEqualTo(ReviewPolarity.NEUTRAL);
    }

    @Test
    void 리뷰에_리뷰이미지를_설정한다() {
        // given
        Review mockReview1 = new Review();
        Review mockReview2 = new Review();
        ReviewImage reviewImage1 = new ReviewImage("www.image1.com", mockReview1);
        ReviewImage reviewImage2 = new ReviewImage("www.image2.com", mockReview2);

        // then
        review.addReviewImage(reviewImage1);
        review.addReviewImage(reviewImage2);

        // then
        assertThat(review.getReviewImages()).hasSize(2);
    }

    @Test
    void 리뷰에_리뷰이미지를_초기화한다() {
        // then
        review.clearReviewImages();

        // then
        assertThat(review.getReviewImages()).isEmpty();
    }
}