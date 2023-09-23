package com.somartreview.reviewmate.service.review;

import com.somartreview.reviewmate.domain.review.ReviewOrderCriteria;
import com.somartreview.reviewmate.domain.review.ReviewProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WidgetReviewSearchCond {

    private ReviewProperty property;
    private String keyword;
    private ReviewOrderCriteria orderCriteria;
}
