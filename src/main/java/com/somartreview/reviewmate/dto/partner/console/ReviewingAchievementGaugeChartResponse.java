package com.somartreview.reviewmate.dto.partner.console;

import com.somartreview.reviewmate.domain.partner.console.AchievementPeriodUnit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ReviewingAchievementGaugeChartResponse {

    @Schema(description = "달성률 기간 단위", example = "QUARTER")
    private AchievementPeriodUnit achievementPeriodUnit;

    @Schema(description = "리뷰 작성률", example = "50")
    private Float reviewingRate;

    @Schema(description = "목표 리뷰 작성률", example = "100")
    private Float targetReviewingRate;

    @Schema(description = "달성률", example = "50")
    private Float reviewingAchievementRate;
}
