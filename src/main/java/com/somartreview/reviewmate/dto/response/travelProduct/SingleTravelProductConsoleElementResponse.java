package com.somartreview.reviewmate.dto.response.travelProduct;

import com.somartreview.reviewmate.domain.TravelProduct.SingleTravelProduct;
import com.somartreview.reviewmate.dto.response.review.ReviewTagResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SingleTravelProductConsoleElementResponse {

    @Schema(description = "파트너사가 정의하는 상품 커스텀 ID (unique)", example = "PRODUCT-0001")
    private String partnerCustomId;

    @Schema(description = "상품 썸네일 이미지 URL")
    private String thumbnailUrl;

    @Schema(description = "상품명", example = "신라더스테이 호텧")
    private String name;

    @Schema(description = "상품 리뷰 수", example = "10")
    private Integer reviewsCount;

    @Schema(description = "상품 평점", example = "4.5")
    private Float rating;

    @Schema(description = "판매자 이름")
    private String partnerSellerName;

    @Schema(description = "판매자 전화번호")
    private String partnerSellerPhoneNumber;

    @Schema(description = "Top 긍정 태그 3개")
    private List<ReviewTagResponse> positiveTags;

    @Schema(description = "Top 부정 태그 3개")
    private List<ReviewTagResponse> negativeTags;

    public SingleTravelProductConsoleElementResponse(final SingleTravelProduct singleTravelProduct) {
        this.partnerCustomId = singleTravelProduct.getTravelProductId().getPartnerCustomId();
        this.thumbnailUrl = singleTravelProduct.getThumbnailUrl();
        this.name = singleTravelProduct.getName();
        this.reviewsCount = singleTravelProduct.getReviews().size();
        this.rating = singleTravelProduct.getRating();
        this.partnerSellerName = singleTravelProduct.getPartnerSeller().getName();
        this.partnerSellerPhoneNumber = singleTravelProduct.getPartnerSeller().getPhoneNumber();
        // TODO: Add positiveTags and negativeTags after implementing reviewTag service
        this.positiveTags = Collections.emptyList();
        this.negativeTags = Collections.emptyList();
    }
}
