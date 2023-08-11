package com.somartreview.reviewmate.web;

import com.somartreview.reviewmate.domain.Review.OrderCriteria;
import com.somartreview.reviewmate.domain.Review.Property;
import com.somartreview.reviewmate.dto.request.review.ReviewCreateRequest;

import javax.validation.Valid;

import com.somartreview.reviewmate.dto.response.review.ReviewInProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "리뷰")
@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    @Operation(operationId = "reviewCreateRequest", summary = "리뷰 생성", description = "formData에 데이터를 넣고 파라미터 별로 Content-Type 구별해서 요청해주세요.")
    @Parameters({
            @Parameter(name = "reviewCreateRequest", description = "리뷰 데이터 객체 \n\nContent-Type: application/json", required = true),
            @Parameter(name = "reviewImages", description = "리뷰 이미지 리스트 \n\nContent-Type: Multipart/form-data", required = false)
    })
    @ApiResponse(responseCode = "201", description = "리뷰 생성 성공", headers = {
            @Header(name = "Location", description = "생성된 리뷰의 URI, /api/v1/review/{reviewId}", schema = @Schema(type = "string"))
    })
    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> createReview(@Valid @RequestPart ReviewCreateRequest reviewCreateRequest,
                                             @RequestPart(required = false) List<MultipartFile> reviewImages) {
        Long reviewId = 1L;

        return ResponseEntity.created(URI.create("/api/v1/review/" + reviewId)).build();
    }

    @Operation(operationId = "findReviewsByTravelProductId", summary = "상품에 등록된 리뷰 조회", description = "상품에 등록된 리뷰를 조회합니다. \n\n리뷰태그의 속성과 키워드, 정렬기준, 페이징를 조회 옵션에 적용할 수 있습니다.")
    @Parameters({
            @Parameter(name = "travelProductId", description = "상품 ID"),
            @Parameter(name = "property", description = "리뷰태그의 속성"),
            @Parameter(name = "keyword", description = "리뷰태그의 키워드 \n\n⚠️ 속성과 키워드를 함께 조회할 때, 해당 속성에 포함되지 않은 키워드를 요청하면 오류가 발생합니다."),
            @Parameter(name = "orderBy", description = "정렬 기준"),
            @Parameter(name = "page", description = "페이지 번호"),
            @Parameter(name = "size", description = "페이지 크기")
    })
    @GetMapping("/products/{travelProductId}")
    public ResponseEntity<List<ReviewInProductResponse>> findReviewsByTravelProductId(@PathVariable Long travelProductId,
                                                                                @RequestParam(required = false, value = "property") Property property,
                                                                                @RequestParam(required = false, value = "keyword") String keyword,
                                                                                @RequestParam(required = false, defaultValue = "LATEST", value = "orderBy") OrderCriteria orderCriteria,
                                                                                @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                                @RequestParam(required = false, defaultValue = "10") Integer size) {

        return ResponseEntity.ok(new ArrayList<ReviewInProductResponse>());
    }
}
