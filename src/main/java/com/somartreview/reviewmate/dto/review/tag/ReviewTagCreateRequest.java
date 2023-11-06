package com.somartreview.reviewmate.dto.review.tag;

import com.somartreview.reviewmate.domain.review.Review;
import com.somartreview.reviewmate.domain.review.ReviewPolarity;
import com.somartreview.reviewmate.domain.review.ReviewProperty;
import com.somartreview.reviewmate.domain.review.tag.ReviewTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewTagCreateRequest {

    private ReviewProperty reviewProperty;
    private String keyword;
    private ReviewPolarity polarity;
    private Integer startIndex;
    private Integer endIndex;

    public ReviewTagCreateRequest(ReviewTagInferenceResponse.Result result) {
        this.reviewProperty = result.getProperty();
        this.keyword = result.getKeyword();
        this.polarity = result.getPolarity();
        this.startIndex = result.getReviewTagIndexResponse().getStartIndex();
        this.endIndex = result.getReviewTagIndexResponse().getEndIndex();
    }

    public ReviewTag toEntity(Review review) {
        return ReviewTag.builder()
                .reviewProperty(reviewProperty)
                .keyword(keyword)
                .polarity(polarity)
                .startIndex(startIndex)
                .endIndex(endIndex)
                .review(review)
                .build();
    }
}
