package com.somartreview.reviewmate.dto.request.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateRequest {

    @NotBlank
    @Schema(description = "고객 이름\n\n⚠️ 업데이트하지 않는 값이라도 제공되어야 합니다.")
    private String name;

    @NotBlank
    @Schema(description = "고객 휴대폰 번호\n\n⚠️ 업데이트하지 않는 값이라도 제공되어야 합니다.")
    private String phoneNumber;

    @NotBlank
    @Schema(description = "고객 카카오톡 아이디\n\n⚠️ 업데이트하지 않는 값이라도 제공되어야 합니다.")
    private String kakaoId;

}
