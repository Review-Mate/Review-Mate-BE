package com.somartreview.reviewmate.dto.request.partnerSeller;

import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.PartnerSeller.PartnerSeller;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PartnerSellerCreateRequest {

    @NotBlank
    @Schema(description = "파트너사의 판매자 이름", example = "권순찬")
    private String name;

    @NotBlank
    @Schema(description = "파트너사의 판매자 전화번호\n\n⚠️ 대시바 없이 숫자만으로", example = "01012345678")
    private String phoneNumber;

    @NotBlank
    @Schema(description = "파트너사의 판매자 카카오 아이디", example = "sckwon770")
    private String kakaoId;

    @NotNull
    @Schema(description = "소속 파트너사의 아이디", example = "1")
    private Long partnerCompanyId;

    public PartnerSeller toEntity(PartnerCompany partnerCompany) {
        return PartnerSeller.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .kakaoId(kakaoId)
                .partnerCompany(partnerCompany)
                .build();
    }
}
