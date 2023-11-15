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
    private Body body;

    @Getter
    @AllArgsConstructor
    public static class Body {
        private String review;
        private List<Result> results;
    }

    @Getter
    @AllArgsConstructor
    public static class Result {
        private ReviewProperty property;
        private ReviewPolarity polarity;
        private String keyword;
        private ReviewTagIndexResponse reviewTagIndexResponse;
    }
}

