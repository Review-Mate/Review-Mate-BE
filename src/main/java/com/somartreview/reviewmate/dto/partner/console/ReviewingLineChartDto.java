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
public class ReviewingLineChartDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "작성률 기간의 시작 날짜 { pattern: 'yyyy-MM-dd'T'HH:mm:ss' }", example = "2023-10-18T13:00:00")
    private LocalDateTime startDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "작성률 기간의 끝 날짜 { pattern: 'yyyy-MM-dd'T'HH:mm:ss' }", example = "2023-10-18T13:00:00")
    private LocalDateTime endDateTime;

    private Float reviewingRate;
}
