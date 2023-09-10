package com.somartreview.reviewmate.dto.partner.seller;

import com.somartreview.reviewmate.domain.partner.seller.PartnerSeller;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PartnerSellerResponse {

    @Schema(description = "파트너사의 판매자 이름", example = "권순찬")
    private String name;

    @Schema(description = "파트너사의 판매자 전화번호\n\n⚠️ 대시바 없이 숫자만으로", example = "01012345678")
    private String phoneNumber;

    @Schema(description = "파트너사의 판매자 카카오 아이디", example = "sckwon770")
    private String kakaoId;

    @Schema(description = "소속 파트너사의 이름", example = "여기어때컴퍼니")
    private String partnerCompanyName;

    public PartnerSellerResponse(final PartnerSeller partnerSeller) {
        this.name = partnerSeller.getName();
        this.phoneNumber = partnerSeller.getPhoneNumber();
        this.kakaoId = partnerSeller.getKakaoId();
        this.partnerCompanyName = partnerSeller.getPartnerCompany().getName();
    }
}
