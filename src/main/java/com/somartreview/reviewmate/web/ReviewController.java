package com.somartreview.reviewmate.web;

import com.somartreview.reviewmate.dto.request.review.ReviewCreateRequest;
import com.somartreview.reviewmate.dto.response.review.ReviewCreateResponse;
import javax.validation.Valid;

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
import java.util.List;

@Tag(name = "리뷰")
@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    @Operation(operationId = "리뷰 생성", summary = "리뷰를 생성", description = "formData에 데이터를 넣고 파라미터 별로 Content-Type 구별해서 요청해주세요.")
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
}
