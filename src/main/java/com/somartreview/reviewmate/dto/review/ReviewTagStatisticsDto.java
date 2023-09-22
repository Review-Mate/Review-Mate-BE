package com.somartreview.reviewmate.dto.review;

import com.somartreview.reviewmate.domain.review.ReviewPolarity;
import com.somartreview.reviewmate.domain.review.ReviewProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewTagStatisticsDto {

    private ReviewProperty property;
    private ReviewPolarity polarity;
    private Long count;
}
