package com.somartreview.reviewmate.web.review;

import com.somartreview.reviewmate.domain.product.SingleTravelProduct;
import com.somartreview.reviewmate.dto.review.ProductReviewTagStatisticsResponse;
import com.somartreview.reviewmate.dto.review.ProductReviewStatisticsResponse;
import com.somartreview.reviewmate.service.products.SingleTravelProductService;
import com.somartreview.reviewmate.service.review.ReviewService;
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

import java.util.List;

@Tag(name = "리뷰 통계 위젯")
@RestController
@RequestMapping("/api/widget/v1")
@RequiredArgsConstructor
public class ReviewStatisticsWidgetController {

    private final ReviewService reviewService;


    @Operation(operationId = "getReviewStatistics", summary = "단일 여행상품에 등록된 리뷰 목록 조회")
    @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr")
    @Parameter(name = "singleTravelProductPartnerCustomId", description = "단일 여행상품의 파트너사 커스텀 ID", example = "PRODUCT_0001")
    @ApiResponse(responseCode = "200", description = "리뷰 조회 성공")
    @ApiResponse(responseCode = "400", description = "존재하지 않는 상품 ID")
    @GetMapping("/{partnerDomain}/products/{singleTravelProductPartnerCustomId}/statistic/reviews")
    public ResponseEntity<ProductReviewStatisticsResponse> getReviewStatistics(@PathVariable String partnerDomain,
                                                                               @PathVariable String singleTravelProductPartnerCustomId) {
        ProductReviewStatisticsResponse productReviewStatisticsResponse = reviewService.getReviewStatisticsResponses(partnerDomain, singleTravelProductPartnerCustomId);

        return ResponseEntity.ok(productReviewStatisticsResponse);
    }


    @Operation(operationId = "getProductReviewTagStatistics", summary = "단일 여행상품에 등록된 리뷰 태그 통계 조회")
    @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr")
    @Parameter(name = "singleTravelProductPartnerCustomId", description = "단일 여행상품의 파트너사 커스텀 ID", example = "PRODUCT_0001")
    @ApiResponse(responseCode = "200", description = "리뷰 조회 성공")
    @ApiResponse(responseCode = "400", description = "존재하지 않는 상품 ID")
    @GetMapping("/{partnerDomain}/products/{singleTravelProductPartnerCustomId}/statistics/tags")
    public ResponseEntity<List<ProductReviewTagStatisticsResponse>> getProductReviewTagStatistics(@PathVariable String partnerDomain,
                                                                                                  @PathVariable String singleTravelProductPartnerCustomId) {
        List<ProductReviewTagStatisticsResponse> productReviewTagStatisticsResponses = reviewService.getProductReviewTagStatisticsResponses(partnerDomain, singleTravelProductPartnerCustomId);

        return ResponseEntity.ok(productReviewTagStatisticsResponses);
    }
}
