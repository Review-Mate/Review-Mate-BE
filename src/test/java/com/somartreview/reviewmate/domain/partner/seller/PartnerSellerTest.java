package com.somartreview.reviewmate.domain.partner.seller;


import com.somartreview.reviewmate.domain.partner.company.PartnerCompany;
import com.somartreview.reviewmate.dto.partner.seller.PartnerSellerUpdateRequest;
import com.somartreview.reviewmate.exception.DomainLogicException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.somartreview.reviewmate.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PartnerSellerTest {

    private PartnerSeller partnerSeller;

    @BeforeEach
    void setup() {
        partnerSeller = PartnerSeller.builder()
                .name("소현진")
                .phoneNumber("01012345678")
                .kakaoId("hyunjin7")
                .partnerCompany(new PartnerCompany())
                .build();
    }

    @Test
    void 파트너사_판매자의_정보를_수정한다() {
        // given
        PartnerSellerUpdateRequest partnerSellerUpdateRequest = new PartnerSellerUpdateRequest("김찬호", "01098765432", "chanho7");

        // when
        partnerSeller.update(partnerSellerUpdateRequest);

        // then
        assertThat(partnerSeller)
                .extracting("name", "phoneNumber", "kakaoId")
                .containsExactly("김찬호", "01098765432", "chanho7");
    }

    @Test
    void 파트너사_판매자의_이름이_공백이어선_안된다() {
        // given
        String name = "";
        PartnerCompany mockPartnerCompany = new PartnerCompany();

        // when & then
        assertThatThrownBy(() -> new PartnerSeller(name, "01012345678", "hyunjin7", mockPartnerCompany))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(PARTNER_SELLER_NAME_ERROR.getMessage());
    }

    @Test
    void 파트너사_판매자의_이름이_255자_보다_길면_안된다() {
        // given
        String name = "a".repeat(256);
        PartnerCompany mockPartnerCompany = new PartnerCompany();

        // when & then
        assertThatThrownBy(() -> new PartnerSeller(name, "01012345678", "hyunjin7", mockPartnerCompany))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(PARTNER_SELLER_NAME_ERROR.getMessage());
    }

    @Test
    void 파트너사_판매자의_휴대폰번호는_숫자로만_이루어져야_한다() {
        // given
        String phoneNumber = "010-1234-5678";
        PartnerCompany mockPartnerCompany = new PartnerCompany();

        // when & then
        assertThatThrownBy(() -> new PartnerSeller("소현진", phoneNumber, "hyunjin7", mockPartnerCompany))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(CUSTOMER_PHONE_NUMBER_ERROR.getMessage());
    }

    @Test
    void 파트너사_판매자의_휴대폰번호는_11자리_숫자여야_한다() {
        // given
        String phoneNumber = "01012345";
        PartnerCompany mockPartnerCompany = new PartnerCompany();

        // when & then
        assertThatThrownBy(() -> new PartnerSeller("소현진", phoneNumber, "hyunjin7", mockPartnerCompany))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(CUSTOMER_PHONE_NUMBER_ERROR.getMessage());
    }
}