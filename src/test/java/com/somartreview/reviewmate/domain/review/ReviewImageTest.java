package com.somartreview.reviewmate.domain.review;

import com.somartreview.reviewmate.domain.review.image.ReviewImage;
import com.somartreview.reviewmate.exception.DomainLogicException;
import org.junit.jupiter.api.Test;

import static com.somartreview.reviewmate.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReviewImageTest {

    @Test
    void 리뷰이미지의_url은_공백이어선_안된다() {
        // given
        String url = " ";
        Review mockReview = new Review();

        // when & then
        assertThatThrownBy(() -> new ReviewImage(url, mockReview))
                .isInstanceOf(DomainLogicException.class)
                .hasMessageContaining(REVIEW_IMAGE_URL_ERROR.getMessage());
    }

    @Test
    void 리뷰이미지의_url은_1024자_보다_길면_안된다() {
        // given
        String url = "a".repeat(1025);
        Review mockReview = new Review();

        // when & then
        assertThatThrownBy(() -> new ReviewImage(url, mockReview))
                .isInstanceOf(DomainLogicException.class)
                .hasMessageContaining(REVIEW_IMAGE_URL_ERROR.getMessage());
    }
}