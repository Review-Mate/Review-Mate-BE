package com.somartreview.reviewmate.dto.request.partnerCompany;

import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PartnerCompanyCreateRequest {

    @Schema(description = "파트너사 도메인", example = "goodchoice.kr")
    private String partnerDomain;

    @Schema(description = "파트너사 이름", example = "여기어때컴퍼니")
    private String name;

    public PartnerCompany toEntity() {
        return PartnerCompany.builder()
                .partnerDomain(partnerDomain)
                .name(name)
                .build();
    }
}
