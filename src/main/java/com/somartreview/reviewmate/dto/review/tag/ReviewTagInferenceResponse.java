package com.somartreview.reviewmate.dto.review.tag;

import com.somartreview.reviewmate.domain.review.ReviewPolarity;
import com.somartreview.reviewmate.domain.review.ReviewProperty;
import com.somartreview.reviewmate.dto.review.ReviewTagIndexResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewTagInferenceResponse {

    private Integer statusCode;
    private List<Result> body;

    @Getter
    @AllArgsConstructor
    public static class Result {
        private ReviewProperty property;
        private String keyword;
        private ReviewPolarity polarity;
        private ReviewTagIndexResponse reviewTagIndexResponse;
    }
}

