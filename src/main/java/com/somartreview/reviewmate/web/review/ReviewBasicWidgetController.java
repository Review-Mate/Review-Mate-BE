package com.somartreview.reviewmate.web.review;

import com.somartreview.reviewmate.domain.review.ReviewOrderCriteria;
import com.somartreview.reviewmate.domain.review.ReviewProperty;
import com.somartreview.reviewmate.dto.review.ReviewCreateRequest;
import com.somartreview.reviewmate.dto.review.ReviewUpdateRequest;
import com.somartreview.reviewmate.dto.review.WidgetReviewResponse;
import com.somartreview.reviewmate.service.review.ReviewDeleteService;
import com.somartreview.reviewmate.service.review.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Tag(name = "리뷰 기본 위젯")
@RestController
@RequestMapping("/api/widget/v1")
@RequiredArgsConstructor
public class ReviewBasicWidgetController {

    private final ReviewService reviewService;
    private final ReviewDeleteService reviewDeleteService;

    @Operation(operationId = "reviewCreateRequest", summary = "리뷰 생성", description = "⚠️ formData에 데이터를 넣고 파라미터 별로 MediaType 구별해서 요청해주세요.")
    @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr")
    @Parameter(name = "travelProductPartnerCustomId", description = "리뷰를 달 상품 ID", example = "PRODUCT_1234")
    @ApiResponse(responseCode = "201", description = "리뷰 생성 성공", headers = {
            @Header(name = "Location", description = "생성된 리뷰의 URI, /api/widget/v1/reviews/{reviewId}", schema = @Schema(type = "string"))
    })
    @PostMapping(value = "{partnerDomain}/reservations/{reservationPartnerCustomId}/reviews", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createReview(@PathVariable String partnerDomain,
                                             @PathVariable String reservationPartnerCustomId,
                                             @Valid @RequestPart ReviewCreateRequest reviewCreateRequest,
                                             @RequestPart(required = false) List<MultipartFile> reviewImageFiles) {
        Long reviewId = reviewService.create(partnerDomain, reservationPartnerCustomId, reviewCreateRequest, reviewImageFiles);

        return ResponseEntity.created(URI.create("/api/widget/v1/reviews/" + reviewId)).build();
    }


    @Operation(operationId = "getWidgetReviewResponseByReviewId", summary = "리뷰 단일 조회")
    @Parameter(name = "reviewId", description = "리뷰 ID")
    @ApiResponse(responseCode = "200", description = "리뷰 조회 성공")
    @ApiResponse(responseCode = "400", description = "존재하지 않는 리뷰 ID")
    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<WidgetReviewResponse> getWidgetReviewResponseByReviewId(@PathVariable Long reviewId) {
        WidgetReviewResponse widgetReviewResponse = reviewService.getWidgetReviewResponseById(reviewId);

        return ResponseEntity.ok(widgetReviewResponse);
    }


    @Operation(operationId = "searchWidgetReviewResponsesWithPaging", summary = "상품에 등록된 리뷰 목록 조회", description = "리뷰태그의 속성과 키워드, 정렬기준, 페이징를 조회 옵션에 적용할 수 있습니다.")
    @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr")
    @Parameter(name = "travelProductPartnerCustomId", description = "여행상품의 파트너사 커스텀 ID", example = "PRODUCT_0001")
    @Parameter(name = "property", description = "리뷰태그의 속성")
    @Parameter(name = "keyword", description = "리뷰태그의 키워드 \n\n⚠️ 속석과 함께 전달되어야 합니다. 속성과 키워드를 매칭되지 않으면, 조회디지 않습니다.")
    @Parameter(name = "orderCriteria", description = "정렬 기준")
    @Parameter(name = "page", description = "페이지 번호 \n\n⚠️ 0 페이지부터 시작")
    @Parameter(name = "size", description = "페이지 크기")
    @ApiResponse(responseCode = "200", description = "리뷰 조회 성공")
    @ApiResponse(responseCode = "400", description = "존재하지 않는 리뷰 ID")
    @GetMapping("{partnerDomain}/products/{travelProductPartnerCustomId}/reviews")
    public ResponseEntity<Page<WidgetReviewResponse>> searchWidgetReviewResponsesWithPaging(@PathVariable String partnerDomain,
                                                                                            @PathVariable String travelProductPartnerCustomId,
                                                                                            @RequestParam(required = false, value = "property") ReviewProperty property,
                                                                                            @RequestParam(required = false) String keyword,
                                                                                            @RequestParam(required = false, defaultValue = "LATEST", value = "orderCriteria") ReviewOrderCriteria orderCriteria,
                                                                                            @RequestParam(required = false, defaultValue = "0", value = "page") Integer page,
                                                                                            @RequestParam(required = false, defaultValue = "10", value = "size") Integer size) {
        Page<WidgetReviewResponse> widgetReviewResponses = reviewService.searchWidgetReviewResponsesWithPaging(partnerDomain, travelProductPartnerCustomId, property, keyword, orderCriteria, page, size);

        return ResponseEntity.ok(widgetReviewResponses);
    }


    @Operation(operationId = "updateReviewByReviewId", summary = "리뷰 수정", description = "⚠️ formData에 데이터를 넣고 파라미터 별로 MediaType 구별해서 요청해주세요.")
    @Parameter(name = "reviewId", description = "리뷰 ID")
    @ApiResponse(responseCode = "204", description = "리뷰 수정 성공")
    @ApiResponse(responseCode = "400", description = "존재하지 않는 리뷰 ID")
    @PatchMapping(value = "/reviews/{reviewId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateReviewByReviewId(@PathVariable Long reviewId,
                                                       @Valid @RequestPart ReviewUpdateRequest reviewUpdateRequest,
                                                       @RequestPart(required = false) List<MultipartFile> reviewImageFiles) {
        reviewService.update(reviewId, reviewUpdateRequest, reviewImageFiles);

        return ResponseEntity.noContent().build();
    }


    @Operation(operationId = "deleteReviewByReviewId", summary = "리뷰 삭제")
    @Parameter(name = "reviewId", description = "리뷰 ID")
    @ApiResponse(responseCode = "204", description = "리뷰 삭제 성공")
    @ApiResponse(responseCode = "400", description = "존재하지 않는 리뷰 ID")
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReviewByReviewId(@PathVariable Long reviewId) {
        reviewDeleteService.deleteReviewByReviewId(reviewId);

        return ResponseEntity.noContent().build();
    }
}
