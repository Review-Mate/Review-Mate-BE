package com.somartreview.reviewmate.dto.partner.manager;

import com.somartreview.reviewmate.domain.partner.manager.PartnerManagerRole;
import com.somartreview.reviewmate.domain.partner.manager.PartnerManager;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PartnerManagerResponse {

    @Schema(description = "파트너사 관리자의 권한", example = "REVIEW_MANAGER")
    private PartnerManagerRole role;

    @Schema(description = "파트너사 관리자의 이름", example = "권순찬")
    private String name;

    @Schema(description = "파트너사 관리자의 이메일", example = "sckwon770@gmail.com")
    private String email;

    @Schema(description = "소속 파트너사의 이름", example = "소마트리뷰")
    private String companyName;

    public PartnerManagerResponse(final PartnerManager partnerManager) {
        this.role = partnerManager.getRole();
        this.name = partnerManager.getName();
        this.email = partnerManager.getEmail();
        this.companyName = partnerManager.getPartnerCompany().getName();
    }
}
