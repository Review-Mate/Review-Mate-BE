package com.somartreview.reviewmate.web;

import com.somartreview.reviewmate.service.dashboard.DashboardService;
import com.somartreview.reviewmate.service.dashboard.TimeSeriesUnit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "대시보드")
@RestController
@RequestMapping("/api/console/v1/")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;


    @Operation(operationId = "getDailyReviewingRate", summary = "리뷰 작성률 조회")
    @Parameter(name = "partnerDomain", description = "고객이 소속될 파트너사 도메인", example = "goodchoice.kr")
    @Parameter(name = "timeSeriesUnit", description = "시계열 단위", example = "DAILY")
    @ApiResponse(responseCode = "200", description = "오늘 리뷰 작성률 조회 성공")
    @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 도메인")
    @GetMapping("/{partnerDomain}/dashboard/reviewing-rate")
    public ResponseEntity<Float> getDailyReviewingRate(@PathVariable String partnerDomain,
                                                       @RequestParam(required = true, value = "timeSeriesUnit") TimeSeriesUnit timeSeriesUnit) {
        Float dailyReviewingRate = dashboardService.getReviewingRate(partnerDomain, timeSeriesUnit);

        return ResponseEntity.ok(dailyReviewingRate);
    }
}
