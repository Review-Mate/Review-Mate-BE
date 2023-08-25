package com.somartreview.reviewmate.dto.request.customer;

import com.somartreview.reviewmate.domain.Customer.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreateRequest {

    @NotEmpty
    @Schema(description = "파트너사가 정의하는 고객의 커스텀 ID (unique) \n\n⚠️ 서로 절대 겹쳐서는 안됨", example = "CUST-1234")
    private String partnerCustomId;

    @NotEmpty
    @Schema(description = "고객 이름", example = "권순찬")
    private String name;

    @NotEmpty
    @Schema(description = "고객 전화번호\n\n⚠️ 대시바 없이 숫자만으로", example = "01012345678")
    private String phoneNumber;

    @NotEmpty
    @Schema(description = "챗봇 메시지를 보내기 위한 고객의 카카오톡 ID", example = "sckwon770")
    private String kakaoId;

    public Customer toEntity(String partnerDomain) {
        return Customer.builder()
                .partnerCustomId(partnerCustomId)
                .partnerDomain(partnerDomain)
                .name(name)
                .phoneNumber(phoneNumber)
                .kakaoId(kakaoId)
                .build();
    }
}
