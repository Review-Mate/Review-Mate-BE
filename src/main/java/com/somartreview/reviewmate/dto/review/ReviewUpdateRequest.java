package com.somartreview.reviewmate.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUpdateRequest {

    @Schema(description = "리뷰 평점 (1 ~ 5)", example = "5")
    @NotNull
    private Integer rating;

    @Schema(description = "리뷰 제목")
    @NotBlank
    private String title;

    @Schema(description = "리뷰 내용")
    @NotBlank
    private String content;
}
