package com.somartreview.reviewmate.dto.review;

import com.somartreview.reviewmate.domain.review.ReviewProperty;
import lombok.Getter;

@Getter
public class ReviewTagClassificationDto {

    private String property;
    private String keyword;

    public ReviewTagClassificationDto(ReviewProperty property, String keyword) {
        this.property = property.toString();
        this.keyword = keyword;
    }
}
