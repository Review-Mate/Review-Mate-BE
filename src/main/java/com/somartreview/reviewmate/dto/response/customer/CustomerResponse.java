package com.somartreview.reviewmate.dto.response.customer;

import com.somartreview.reviewmate.domain.Customer.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    @Schema(description = "고객 ID")
    private Long id;

    @Schema(description = "파트너사가 정의한 상품 커스텀 ID (unique)", example = "CUST-1234")
    private String partnerCustomerId;

    @Schema(description = "고객 이름", example = "권순찬")
    private String name;

    @Schema(description = "고객 전화번호\n\n⚠️ 대시바 없이 숫자만으로", example = "01012345678")
    private String phoneNumber;

    @Schema(description = "챗봇 메시지를 보내기 위한 고객의 카카오톡 ID", example = "sckwon770")
    private String kakaoId;

    public CustomerResponse(final Customer customer) {
        this.id = customer.getId();
        this.partnerCustomerId = customer.getPartnerCustomId();
        this.name = customer.getName();
        this.phoneNumber = customer.getPhoneNumber();
        this.kakaoId = customer.getKakaoId();
    }
}
