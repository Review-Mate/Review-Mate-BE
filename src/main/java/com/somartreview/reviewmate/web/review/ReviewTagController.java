package com.somartreview.reviewmate.web.review;

import com.somartreview.reviewmate.dto.review.tag.ReviewTagClassificationDto;
import com.somartreview.reviewmate.service.review.ReviewTagService;
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

import java.util.*;

// Impl the requests (상품에 달려있는 태그들 조회, 평점+탑 태그와 같은 리뷰 통계)
@Tag(name = "리뷰 태그 및 감정 분석 데이터")
@RequestMapping("/api/widget/v1")
@RestController
@RequiredArgsConstructor
public class ReviewTagController {

    private final ReviewTagService reviewTagService;


    @Operation(operationId = "getDistinctReviewTagClassification", summary = "상품에서 필터링할 리뷰의 속성과 키워드 목록 조회", description = "상품에 등록된 리뷰의 분석 결과를 바탕으로 리뷰의 속성과 키워드들을 조회합니다. \n⚠️ 현재는 등록된 모든 속성과 키워드를 조회합니다. (threshold 없음)")
    @Parameter(name = "partnerDomain", description = "파트너사 도메인", example = "goodchoice.kr")
    @Parameter(name = "travelProductCustomId", description = "단일 여행상품의 파트너사 커스텀 ID", example = "PRODUCT_0001")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "400", description = "존재하지 않는 파트너사 도메인 혹은 여행상품 파트너사 커스텀 ID")
    @GetMapping("{partnerDomain}/products/{travelProductCustomId}/tags")
    public ResponseEntity<Map<String, Set<String>>> getDistinctReviewTagClassification(@PathVariable String partnerDomain,
                                                                    @PathVariable String travelProductCustomId) {
        List<ReviewTagClassificationDto> reviewTagClassificationDtos = reviewTagService.getDistinctReviewTagClassificationDtosByPartnerDomainAndTravelProductPartnerCustomId(partnerDomain, travelProductCustomId);

        Map<String, Set<String>> propertiesKeywords = new HashMap<>();
        for (ReviewTagClassificationDto dto : reviewTagClassificationDtos) {
            String property = dto.getProperty();
            String keyword = dto.getKeyword();

            Set<String> keywords = propertiesKeywords.getOrDefault(property, new HashSet<>());
            keywords.add(keyword);

            propertiesKeywords.put(property, keywords);
        }

        return ResponseEntity.ok(propertiesKeywords);
    }
}