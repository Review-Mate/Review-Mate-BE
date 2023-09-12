package com.somartreview.reviewmate.domain.partner.company;

import com.somartreview.reviewmate.dto.partner.company.PartnerCompanyUpdateRequest;
import com.somartreview.reviewmate.exception.DomainLogicException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.somartreview.reviewmate.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PartnerCompanyTest {

    private PartnerCompany partnerCompany;

    @BeforeEach
    void setup() {
        partnerCompany = PartnerCompany.builder()
                .partnerDomain("goodchoice.kr")
                .name("여기어때컴퍼니")
                .build();
    }

    @Test
    void 파트너사_정보를_수정한다() {
        // given
        PartnerCompanyUpdateRequest partnerCompanyUpdateRequest = new PartnerCompanyUpdateRequest("yanolja.com", "야놀자");

        // when
        partnerCompany.update(partnerCompanyUpdateRequest);

        // then
        assertThat(partnerCompany)
                .extracting("partnerDomain", "name")
                .containsExactly("yanolja.com", "야놀자");
    }

    @Test
    void 파트너사의_이름이_공백이어선_안된다() {
        // given
        name = " ";

        // when & then
        assertThatThrownBy(() -> new PartnerCompany("goodchoice.kr", emptyName))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(PARTNER_COMPANY_NAME_ERROR.getMessage());
    }

    @Test
    void 파트너사의_이름이_255자_보다_길면_안된다() {
        // given
        name = "a".repeat(256);

        // when & then
        assertThatThrownBy(() -> new PartnerCompany("goodchoice.kr", longName))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(PARTNER_COMPANY_NAME_ERROR.getMessage());
    }
}