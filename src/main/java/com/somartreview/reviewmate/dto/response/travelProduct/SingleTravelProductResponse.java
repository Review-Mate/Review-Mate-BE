package com.somartreview.reviewmate.dto.response.travelProduct;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.somartreview.reviewmate.domain.TravelProduct.SingleTravelProduct;
import com.somartreview.reviewmate.dto.response.review.ReviewTagResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SingleTravelProductResponse {

    @Schema(description = "파트너사가 정의한 상품 커스텀 ID", example = "PRODUCT-0001")
    private String partnerCustomerId;

    @Schema(description = "상품 썸네일 이미지 URL")
    private String thumbnailUrl;

    @Schema(description = "상품명", example = "신라더스테이 호텧")
    private String name;

    @Schema(description = "상품 평점", example = "4.5")
    private Float rating;

    @Schema(description = "긍정 태그들")
    private List<ReviewTagResponse> positiveTags;

    @Schema(description = "부정 태그들")
    private List<ReviewTagResponse> negativeTags;

    @Schema(description = "파트너사 ID")
    private Long partnerCompanyId;

    @Schema(description = "파트너사의 판매자 ID")
    private Long partnerSellerId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd'T'HH:mm")
    @Schema(description = "여행상품 이용 시작시간", example = "2023.08.14T15:00")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd'T'HH:mm")
    @Schema(description = "여행상품 이용 종료시간", example = "2023.08.15T12:00")
    private LocalDateTime endTime;

    public SingleTravelProductResponse(final SingleTravelProduct singleTravelProduct) {
        this.partnerCustomerId = singleTravelProduct.getTravelProductId().getPartnerCustomId();
        this.thumbnailUrl = singleTravelProduct.getThumbnailUrl();
        this.name = singleTravelProduct.getName();
        this.rating = singleTravelProduct.getRating();
        // TODO: Add positiveTags and negativeTags after implementing reviewTag service
        this.positiveTags = Collections.emptyList();
        this.negativeTags = Collections.emptyList();
        this.partnerCompanyId = singleTravelProduct.getPartnerCompany().getId();
        this.partnerSellerId = singleTravelProduct.getPartnerSeller().getId();
        this.startTime = singleTravelProduct.getStartTime();
        this.endTime = singleTravelProduct.getEndTime();
    }
}
