package com.somartreview.reviewmate.dto.partner.console;

import com.somartreview.reviewmate.domain.partner.console.AchievementPeriodUnit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class PartnerConsoleConfigUpdateRequest {

    @NotNull
    @Schema(description = "목표 리뷰 작성률")
    private Float targetReviewingRate;

    @NotNull
    @Schema(description = "달성률 기간 단위")
    private AchievementPeriodUnit achievementPeriodUnit;
}
