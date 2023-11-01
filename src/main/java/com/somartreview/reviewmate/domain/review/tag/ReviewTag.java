package com.somartreview.reviewmate.domain.review.tag;

import com.querydsl.core.annotations.QueryInit;
import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.review.Review;
import com.somartreview.reviewmate.domain.review.ReviewPolarity;
import com.somartreview.reviewmate.domain.review.ReviewProperty;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(indexes = {
        @Index(name = "idx_review_id_property_keyword", columnList = "review_id, property, keyword"),
        @Index(name = "idx_review_id_property_polarity", columnList = "review_id, property, polarity")
})
public class ReviewTag extends BaseEntity {

    private static final int MAX_REVIEW_CONTENT_LENGTH = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_tag_id")
    private Long id;

    @Column(nullable = false, name = "property")
    @Enumerated(EnumType.STRING)
    private ReviewProperty reviewProperty;

    private String keyword;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReviewPolarity polarity;

    @Column(nullable = false)
    private Integer startIndex;

    @Column(nullable = false)
    private Integer endIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    @QueryInit("reservation.travelProduct")
    private Review review;

    @Builder
    public ReviewTag(ReviewProperty reviewProperty, String keyword, ReviewPolarity polarity, Integer startIndex, Integer endIndex, Review review) {
        this.reviewProperty = reviewProperty;
        this.keyword = keyword;
        this.polarity = polarity;
        validateStartIndex(startIndex);
        this.startIndex = startIndex;
        validateEndIndex(endIndex);
        validateFrontEndIndex(startIndex, endIndex);
        this.endIndex = endIndex;
        this.review = review;
    }

    private void validateStartIndex(Integer startIndex) {
        if (startIndex < 0 || startIndex > MAX_REVIEW_CONTENT_LENGTH) {
            throw new DomainLogicException(ErrorCode.REVIEW_TAG_OUT_OF_BOUND_INDEX_ERROR, "startIndex");
        }
    }

    private void validateEndIndex(Integer endIndex) {
        if (endIndex < 0 || endIndex > MAX_REVIEW_CONTENT_LENGTH) {
            throw new DomainLogicException(ErrorCode.REVIEW_TAG_OUT_OF_BOUND_INDEX_ERROR, "endIndex");
        }
    }

    private void validateFrontEndIndex(Integer startIndex, Integer endIndex) {
        if (startIndex > endIndex) {
            throw new DomainLogicException(ErrorCode.REVIEW_TAG_FRONT_END_INDEX_ERROR);
        }
    }
}
