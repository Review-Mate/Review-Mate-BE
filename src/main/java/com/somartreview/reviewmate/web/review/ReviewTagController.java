package com.somartreview.reviewmate.web.review;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "리뷰 태그 및 감정 분석 데이터")
@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class ReviewTagController {
    // TODO: REVIEW CONTROLLER (+ 상품에 달려있는 태그들 조회, 평점+탑 태그와 같은 리뷰 통계)
}
