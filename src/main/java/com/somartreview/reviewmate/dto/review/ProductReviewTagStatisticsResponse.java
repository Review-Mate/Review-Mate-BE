package com.somartreview.reviewmate.dto.review;

import com.somartreview.reviewmate.domain.review.ReviewProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductReviewTagStatisticsResponse {

    private ReviewProperty reviewProperty;
    private Long positiveCount;
    private Long negativeCount;

    public ProductReviewTagStatisticsResponse(ReviewProperty reviewProperty) {
        this.reviewProperty = reviewProperty;
        this.positiveCount = 0L;
        this.negativeCount = 0L;
    }

    public void setPositiveCount(Long positiveCount) {
        this.positiveCount = positiveCount;
    }

    public void setNegativeCount(Long negativeCount) {
        this.negativeCount = negativeCount;
    }


}
