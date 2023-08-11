package com.somartreview.reviewmate.domain.Review;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ReviewTag extends BaseEntity {

    private static final int MAX_REVIEW_CONTENT_LENGTH = 255;

    @Id
    @GeneratedValue
    @Column(name = "review_tag_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Property property;

    private String keyword;

    @Column(nullable = false)
    private Double polarityValue;

    @Column(nullable = false)
    private Integer startIndex;

    @Column(nullable = false)
    private Integer endIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    public ReviewTag(Property property, String keyword, Double polarityValue, Integer startIndex, Integer endIndex, Review review) {
        this.property = property;
        this.keyword = keyword;
        this.polarityValue = polarityValue;
        validateStartIndex(startIndex);
        this.startIndex = startIndex;
        validateEndIndex(endIndex);
        this.endIndex = endIndex;
        review.addReviewTag(this);
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
}
