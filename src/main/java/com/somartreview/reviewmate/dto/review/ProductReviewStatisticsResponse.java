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
    private Integer fiveStarRatingCount;

    @Schema(description = "4점 리뷰 개수")
    private Integer fourStarRatingCount;

    @Schema(description = "3점 리뷰 개수")
    private Integer threeStarRatingCount;

    @Schema(description = "2점 리뷰 개수")
    private Integer twoStarRatingCount;

    @Schema(description = "1점 리뷰 개수")
    private Integer oneStarRatingCount;

}
