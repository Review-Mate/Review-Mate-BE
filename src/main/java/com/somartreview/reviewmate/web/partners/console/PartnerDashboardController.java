package com.somartreview.reviewmate.web.partners.console;

import com.somartreview.reviewmate.dto.partner.console.ReviewingAchievementBarChartResponse;
import com.somartreview.reviewmate.dto.partner.console.ReviewingAchievementGaugeChartResponse;
import com.somartreview.reviewmate.service.partners.console.PartnerDashboardService;
import com.somartreview.reviewmate.domain.partner.console.ConsoleTimeSeriesUnit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "관리자 대시보드")
@RestController
@RequestMapping("/api/console/v1/")
@RequiredArgsConstructor
public class PartnerDashboardController {

    private final PartnerDashboardService partnerDashboardService;


    @Operation(operationId = "getReviewingRate", summary = "시계열 단위별 리뷰 작성률 조회")
    @Parameter(name = "partnerDomain", description = "대시보드 대상인 파트너사 도메인", example = "goodchoice.kr")
    @Parameter(name = "timeSeriesUnit", description = "시계열 단위", example = "DAILY")
    @ApiResponse(responseCode = "200", description = "리뷰 작성률 조회 성공")
    @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 도메인")
    @GetMapping("/{partnerDomain}/dashboard/reviewing-rate")
    public ResponseEntity<Float> getDailyReviewingRate(@PathVariable String partnerDomain,
                                                       @RequestParam(required = true, value = "consoleTimeSeriesUnit") ConsoleTimeSeriesUnit consoleTimeSeriesUnit) {
        LocalDateTime now = LocalDateTime.now();
        Float dailyReviewingRate = partnerDashboardService.getReviewingRate(partnerDomain, now, consoleTimeSeriesUnit);

        return ResponseEntity.ok(dailyReviewingRate);
    }


    @Operation(operationId = "getTotalReviewCount", summary = "누적 리뷰 수 조회")
    @Parameter(name = "partnerDomain", description = "대시보드 대상인 파트너사 도메인", example = "goodchoice.kr")
    @ApiResponse(responseCode = "200", description = "누적 리뷰 수 조회 성공")
    @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 도메인")
    @GetMapping("/{partnerDomain}/dashboard/total-review-count")
    public ResponseEntity<Long> getTotalReviewCount(@PathVariable String partnerDomain) {
        Long totalReviewCount = partnerDashboardService.getTotalReviewCount(partnerDomain);

        return ResponseEntity.ok(totalReviewCount);
    }


    @Operation(operationId = "getReviewingAchievementGaugeChart", summary = "달성률의 게이지 그래프 정보 조회", description = "달성률 = {(현재 리뷰 작성률)/(목표 리뷰 작성률)} * 100")
    @Parameter(name = "partnerDomain", description = "대시보드 대상인 파트너사 도메인", example = "goodchoice.kr")
    @ApiResponse(responseCode = "200", description = "누적 리뷰 수 조회 성공")
    @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 도메인")
    @GetMapping("/{partnerDomain}/dashboard/reviewing-achievement-gauge-chart")
    public ResponseEntity<ReviewingAchievementGaugeChartResponse> getReviewingAchievementGaugeChart(@PathVariable String partnerDomain) {
        ReviewingAchievementGaugeChartResponse reviewingAchievement = partnerDashboardService.getReviewingAchievementGaugeChart(partnerDomain);

        return ResponseEntity.ok(reviewingAchievement);
    }


    @Operation(operationId = "getReviewingAchievementBarChart", summary = "달성률의 막대 그래프 정보 조회", description = "시계열 단위와 상관없이 가로축 8칸을 조회합니다. \n달성률 = {(현재 리뷰 작성률)/(목표 리뷰 작성률)} * 100")
    @Parameter(name = "partnerDomain", description = "대시보드 대상인 파트너사 도메인", example = "goodchoice.kr")
    @ApiResponse(responseCode = "200", description = "누적 리뷰 수 조회 성공")
    @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 도메인")
    @GetMapping("/{partnerDomain}/dashboard/reviewing-achievement-bar-chart")
    public ResponseEntity<List<ReviewingAchievementBarChartResponse>> getReviewingAchievementBarChart(@PathVariable String partnerDomain) {
        List<ReviewingAchievementBarChartResponse> reviewingAchievement = partnerDashboardService.getReviewingAchievementBarChart(partnerDomain);

        return ResponseEntity.ok(reviewingAchievement);
    }
}
