package com.somartreview.reviewmate.dto.partner.company;

import com.somartreview.reviewmate.domain.partner.company.PartnerCompany;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PartnerCompanyResponse {

    @Schema(description = "파트너사 ID")
    private Long id;

    @Schema(description = "파트너사 이름")
    private String name;

    public PartnerCompanyResponse(final PartnerCompany partnerCompany) {
        this.id = partnerCompany.getId();
        this.name = partnerCompany.getName();
    }
}
