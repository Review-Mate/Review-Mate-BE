package com.somartreview.reviewmate.web;

import com.somartreview.reviewmate.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "대시보드")
@RestController
@RequestMapping("/api/console/v1/")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;


    @Operation(operationId = "getDailyReviewingRate", summary = "오늘 리뷰 작성률 조회")
    @Parameter(name = "partnerDomain", description = "고객이 소속될 파트너사 도메인", example = "goodchoice.kr")
    @ApiResponse(responseCode = "200", description = "오늘 리뷰 작성률 조회 성공")
    @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 도메인")
    @GetMapping("/{partnerDomain}/dashboard/daily-reviewing-rate")
    public ResponseEntity<Float> getDailyReviewingRate(@PathVariable String partnerDomain) {
        Float dailyReviewingRate = dashboardService.getDailyReviewingRate(partnerDomain);

        return ResponseEntity.ok(dailyReviewingRate);
    }
}
