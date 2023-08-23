package com.somartreview.reviewmate.dto.response.review;

import com.somartreview.reviewmate.domain.Review.ReviewTag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewHighlightPairResponse {

    @Schema(description = "문자열에서 태그가 감지된 부분의 시작 인덱스")
    private Integer startIndex;

    @Schema(description = "문자열에서 태그가 감지된 부분의 끝 인덱스")
    private Integer endIndex;

    public ReviewHighlightPairResponse(final ReviewTag reviewTag) {
        this.startIndex = reviewTag.getStartIndex();
        this.endIndex = reviewTag.getEndIndex();
    }
}
