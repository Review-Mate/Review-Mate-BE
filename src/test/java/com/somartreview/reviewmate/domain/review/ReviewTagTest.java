package com.somartreview.reviewmate.domain.review;

import com.somartreview.reviewmate.domain.review.tag.ReviewTag;
import com.somartreview.reviewmate.exception.DomainLogicException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.somartreview.reviewmate.exception.ErrorCode.REVIEW_TAG_FRONT_END_INDEX_ERROR;
import static com.somartreview.reviewmate.exception.ErrorCode.REVIEW_TAG_OUT_OF_BOUND_INDEX_ERROR;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReviewTagTest {

    private ReviewTag reviewTag;

    @BeforeEach
    void setUp() {
        reviewTag = ReviewTag.builder()
                .reviewProperty(ReviewProperty.CLEANNESS)
                .keyword("키워드")
                .polarity(ReviewPolarity.POSITIVE)
                .startIndex(0)
                .endIndex(1)
                .build();
    }


    @Test
    void 리뷰태그의_시작인덱스가_0보다_작으면_안된다() {
        // given
        Review mockReview = new Review();
        int startIndex = -1;

        // when & then
        assertThatThrownBy(() -> new ReviewTag(ReviewProperty.LOCATION, "키워드", ReviewPolarity.POSITIVE, startIndex, 1, mockReview))
                .isInstanceOf(DomainLogicException.class)
                .hasMessageContaining(REVIEW_TAG_OUT_OF_BOUND_INDEX_ERROR.getMessage());
    }

    @Test
    void 리뷰태그의_시작인덱스가_리뷰의_최대길이_보다_길면_안된다() {
        // given
        Review mockReview = new Review();
        int startIndex = 256;

        // when & then
        assertThatThrownBy(() -> new ReviewTag(ReviewProperty.LOCATION, "키워드", ReviewPolarity.POSITIVE, startIndex, 1, mockReview))
                .isInstanceOf(DomainLogicException.class)
                .hasMessageContaining(REVIEW_TAG_OUT_OF_BOUND_INDEX_ERROR.getMessage());
    }

    @Test
    void 리뷰태그의_끝인덱스가_0보다_작으면_안된다() {
        // given
        Review mockReview = new Review();
        int endIndex = -1;

        // when & then
        assertThatThrownBy(() -> new ReviewTag(ReviewProperty.LOCATION, "키워드", ReviewPolarity.POSITIVE, 0, endIndex, mockReview))
                .isInstanceOf(DomainLogicException.class)
                .hasMessageContaining(REVIEW_TAG_OUT_OF_BOUND_INDEX_ERROR.getMessage());
    }

    @Test
    void 리뷰태그의_끝인덱스가_리뷰의_최대길이_보다_길면_안된다() {
        // given
        Review mockReview = new Review();
        int endIndex = 256;

        // when & then
        assertThatThrownBy(() -> new ReviewTag(ReviewProperty.LOCATION, "키워드", ReviewPolarity.POSITIVE, 0, endIndex, mockReview))
                .isInstanceOf(DomainLogicException.class)
                .hasMessageContaining(REVIEW_TAG_OUT_OF_BOUND_INDEX_ERROR.getMessage());
    }

    @Test
    void 리뷰태그의_끝인덱스가_시작인덱스_보다_앞이면_안된다() {
        // given
        Review mockReview = new Review();
        int startIndex = 20;
        int endIndex = 10;

        // when & then
        assertThatThrownBy(() -> new ReviewTag(ReviewProperty.LOCATION, "키워드", ReviewPolarity.POSITIVE, startIndex, endIndex, mockReview))
                .isInstanceOf(DomainLogicException.class)
                .hasMessageContaining(REVIEW_TAG_FRONT_END_INDEX_ERROR.getMessage());
    }
}