package com.somartreview.reviewmate.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductReviewStatisticsResponse {

    @Schema(description = "평균 평점")
    private Float averageRating;

    @Schema(description = "리뷰 개수")
    private Long reviewCount;

    @Schema(description = "5점 리뷰 개수")
    private Long fiveStarRatingCount;

    @Schema(description = "4점 리뷰 개수")
    private Long fourStarRatingCount;

    @Schema(description = "3점 리뷰 개수")
    private Long threeStarRatingCount;

    @Schema(description = "2점 리뷰 개수")
    private Long twoStarRatingCount;

    @Schema(description = "1점 리뷰 개수")
    private Long oneStarRatingCount;

}
