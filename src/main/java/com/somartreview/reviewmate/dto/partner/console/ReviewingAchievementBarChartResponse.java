package com.somartreview.reviewmate.dto.partner.console;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ReviewingAchievementBarChartResponse {

    @Schema(description = "시계열별 달성률 데이터\n 시계열 단위와 상관없이 가로축 8칸을 조회합니다.")
    List<ReviewingAchievementBarChartDto> reviewingAchievementBarChartDtos;
}
