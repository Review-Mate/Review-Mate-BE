package com.somartreview.reviewmate.dto.request.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateRequest {

    @NotBlank
    @Schema(description = "고객 이름")
    private String name;

    @NotBlank
    @Schema(description = "고객 휴대폰 번호")
    private String phoneNumber;

    @NotBlank
    @Schema(description = "고객 카카오톡 아이디")
    private String kakaoId;

}
