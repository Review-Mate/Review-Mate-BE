package com.somartreview.reviewmate.dto.request.partnerSeller;

import com.somartreview.reviewmate.domain.partnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.partnerSeller.PartnerSeller;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PartnerSellerCreateRequest {

    @NotBlank
    @Schema(description = "판매자 이름", example = "권순찬")
    private String name;

    @NotBlank
    @Schema(description = "판매자 전화번호\n\n⚠️ 대시바 없이 숫자만으로", example = "01012345678")
    private String phoneNumber;

    @NotBlank
    @Schema(description = "판매자 카카오 아이디", example = "sckwon770")
    private String kakaoId;

    @NotBlank
    @Schema(description = "소속된 파트너사의 도메인", example = "goodchoice.kr")
    private String partnerCompanyDomain;

    public PartnerSeller toEntity(PartnerCompany partnerCompany) {
        return PartnerSeller.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .kakaoId(kakaoId)
                .partnerCompany(partnerCompany)
                .build();
    }
}
