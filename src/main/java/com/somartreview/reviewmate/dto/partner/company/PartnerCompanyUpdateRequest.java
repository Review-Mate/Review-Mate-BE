package com.somartreview.reviewmate.dto.partner.company;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PartnerCompanyUpdateRequest {

    @Schema(description = "파트너사 도메인", example = "goodchoice.kr")
    private String partnerDomain;

    @Schema(description = "파트너사 이름", example = "여기어때컴퍼니")
    private String name;
}
