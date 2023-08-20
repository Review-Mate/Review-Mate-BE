package com.somartreview.reviewmate.dto.request.partnerManager;

import com.somartreview.reviewmate.domain.PartnerCompany.PartnerCompany;
import com.somartreview.reviewmate.domain.PartnerManager.PartnerManager;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class PartnerManagerCreateRequest {

    @NotBlank
    @Schema(description = "파트너사 관리자의 이름", example = "권순찬")
    private String name;

    @NotBlank
    @Schema(description = "파트너사 관리자의 이메일", example = "sckwon770@gmail.com")
    private String email;

    @NotBlank
    @Schema(description = "파트너사 관리자의 비밀번호\n\n⚠️ 비밀번호는 8자리 이상이어야 함", example = "password1234")
    private String password;

    @NotBlank
    @Schema(description = "소속 파트너사의 도메인", example = "goodchoice.kr")
    private String partnerCompanyDomain;

    public PartnerManager toEntity(PartnerCompany partnerCompany) {
        return PartnerManager.builder()
                .name(name)
                .email(email)
                .password(password)
                .partnerCompany(partnerCompany)
                .build();
    }
}
