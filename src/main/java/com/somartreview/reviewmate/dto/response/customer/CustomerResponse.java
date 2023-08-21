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

    @Schema(description = "클라이언트 서비스에서의 유저 식별자\n\n⚠️ 반드시 유니크 해야함", example = "CUST-1234")
    private String partnerCustomerId;

    @Schema(description = "고객 이름", example = "권순찬")
    private String name;

    @Schema(description = "고객 전화번호\n\n⚠️ 대시바 없이 숫자만으로", example = "01012345678")
    private String phoneNumber;

    @Schema(description = "챗봇 메시지를 보내기 위한 고객의 카카오톡 ID", example = "sckwon770")
    private String kakaoId;

    public CustomerResponse(final Customer customer) {
        this.partnerCustomerId = customer.getPartnerCustomerId();
        this.name = customer.getName();
        this.phoneNumber = customer.getPhoneNumber();
        this.kakaoId = customer.getKakaoId();
    }
}
