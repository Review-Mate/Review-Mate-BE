package com.somartreview.reviewmate.dto.partner.console;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ReviewingAchievementBarChartDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "작성률 측정 기간의 시작 날짜 { pattern: 'yyyy-MM-dd'T'HH:mm:ss' }", example = "2023-10-18T13:00:00")
    private LocalDateTime startDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "작성률 측정 기간의 끝 날짜 { pattern: 'yyyy-MM-dd'T'HH:mm:ss' }", example = "2023-10-18T13:00:00")
    private LocalDateTime endDateTime;

    @Schema(description = "현재 작성률", example = "50")
    private Float reviewingRate;

    @Schema(description = "목표 작성률", example = "100")
    private Float targetReviewingRate;
}
