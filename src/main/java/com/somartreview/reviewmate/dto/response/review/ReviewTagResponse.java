package com.somartreview.reviewmate.dto.response.review;

import com.somartreview.reviewmate.domain.Review.ReviewProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewTagResponse {

    @Schema(description = "태그 이름", example = "청결")
    private ReviewProperty category;

    @Schema(description = "태그를 포함한 리뷰의 수", example = "10")
    private Integer count;
}
