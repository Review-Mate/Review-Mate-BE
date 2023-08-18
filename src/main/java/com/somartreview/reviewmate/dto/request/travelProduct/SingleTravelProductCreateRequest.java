package com.somartreview.reviewmate.dto.request.travelProduct;

import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.PartnerSeller.PartnerSeller;
import com.somartreview.reviewmate.domain.TravelProduct.Category;
import com.somartreview.reviewmate.domain.TravelProduct.SingleTravelProduct;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SingleTravelProductCreateRequest {

    @NotBlank
    @Schema(description = "클라이언트 서비스에서의 상품 식별자", example = "PRODUCT-0001")
    private String partnerSingleTravelProductId;

    @NotBlank
    @Schema(description = "상품명", example = "신라더스테이 호텧")
    private String name;

    @NotNull
    @Schema(description = "파트너사 ID")
    private Long partnerCompanyId;

    @NotNull
    @Schema(description = "파트너사의 판매자 ID")
    private Long partnerSellerId;

    @NotNull
    @DateTimeFormat(pattern = "yyyy.MM.dd'T'HH:mm")
    @Schema(description = "여행상품 이용 시작시간", example = "2023.08.14T15:00")
    private LocalDateTime startTime;

    @NotNull
    @DateTimeFormat(pattern = "yyyy.MM.dd'T'HH:mm")
    @Schema(description = "여행상품 이용 종료시간", example = "2023.08.15T12:00")
    private LocalDateTime endTime;

    @NotBlank
    @Schema(description = "여행상품 카테고리", example = "ACCOMMODATION")
    private Category category;

    public SingleTravelProduct toEntity(String thumbnailUrl ,PartnerCompany partnerCompany, PartnerSeller partnerSeller) {
        return SingleTravelProduct.builder()
                .partnerSingleTravelProductId(partnerSingleTravelProductId)
                .thumbnailUrl(thumbnailUrl)
                .name(name)
                .partnerCompany(partnerCompany)
                .partnerSeller(partnerSeller)
                .startTime(startTime)
                .endTime(endTime)
                .category(category)
                .build();
    }
}
