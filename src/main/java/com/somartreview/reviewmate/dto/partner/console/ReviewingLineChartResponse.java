package com.somartreview.reviewmate.dto.partner.console;

import com.somartreview.reviewmate.domain.product.SingleTravelProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@Builder
public class ReviewingLineChartResponse {

    @Schema(description = "카테고리 목록")
    private List<SingleTravelProductCategory> categories;

    @Schema(description = "카테고리별 리뷰 작성률 데이터\n 시계열 단위와 상관없이 가로축 24칸을 조회합니다.")
    private Map<SingleTravelProductCategory, List<ReviewingLineChartDto>> categoriesReviewingLineGraphData;
}
