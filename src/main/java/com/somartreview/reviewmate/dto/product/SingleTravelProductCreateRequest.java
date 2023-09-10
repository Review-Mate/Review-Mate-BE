package com.somartreview.reviewmate.dto.product;

import com.somartreview.reviewmate.domain.partner.company.PartnerCompany;
import com.somartreview.reviewmate.domain.partner.seller.PartnerSeller;
import com.somartreview.reviewmate.domain.product.SingleTravelProductCategory;
import com.somartreview.reviewmate.domain.product.SingleTravelProduct;
import com.somartreview.reviewmate.exception.EnumNotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SingleTravelProductCreateRequest {

    @NotBlank
    @Schema(description = "파트너사가 정의하는 상품 커스텀 ID (unique)\n\n⚠️ 서로 절대 겹치면 안됨", example = "PRODUCT-0001")
    private String partnerCustomId;

    @NotBlank
    @Schema(description = "상품명", example = "신라더스테이 호텧")
    private String name;

    @EnumNotNull
    @Schema(description = "여행상품 카테고리", example = "ACCOMMODATION")
    private SingleTravelProductCategory singleTravelProductCategory;

    @NotNull
    @Schema(description = "파트너사의 판매자 ID")
    private Long partnerSellerId;

    public SingleTravelProduct toEntity(String thumbnailUrl, PartnerCompany partnerCompany, PartnerSeller partnerSeller) {
        return SingleTravelProduct.builder()
                .partnerCustomId(partnerCustomId)
                .thumbnailUrl(thumbnailUrl)
                .name(name)
                .singleTravelProductCategory(singleTravelProductCategory)
                .partnerCompany(partnerCompany)
                .partnerSeller(partnerSeller)
                .build();
    }
}
