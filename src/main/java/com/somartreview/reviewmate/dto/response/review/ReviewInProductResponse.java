package com.somartreview.reviewmate.dto.response.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewInProductResponse {

    @Schema(description = "리뷰 ID", example = "1")
    private Long id;

    @Schema(description = "리뷰 평점 (1 ~ 5)", example = "5")
    private Integer rating;

    @Schema(description = "리뷰 제목", example = "최고의 펜션")
    private String title;

    @Schema(description = "리뷰 내용", example = "바다뷰도 끝내주고, 너무 행복한 시간이였어요. 다음에 또 오고 싶어요.")
    private String content;

    @Schema(description = "리뷰를 단 고객의 이름", example = "권순찬")
    private String authorName;

    @Schema(description = "업로드 날짜", example = "2021.01.01")
    private String createdAt;

    @Schema(description = "긍부정 수치 (양수 = 긍정, 음수 = 부정)", example = "10.18")
    private Double polarityValue;

    @Schema(description = "적용된 속성 혹은 키워드가 포함된 문자열의 인덱스")
    private List<ReviewHighlightPair> reviewHighlightPairs;
}
