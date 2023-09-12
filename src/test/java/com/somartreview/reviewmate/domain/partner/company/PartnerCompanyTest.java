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
        String emptyName = " ";

        // when & then
        assertThatThrownBy(() -> new PartnerCompany("goodchoice.kr", emptyName))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(PARTNER_COMPANY_NAME_ERROR.getMessage());
    }

    @Test
    void 파트너사의_이름이_255자_보다_길면_안된다() {
        // given
        String overflowName = "WTy85YZG2HsEOMahKZERNJmZrY4XHnOwugREhMcgSxYrqWnqJa9yGgBLojDzLF8l6Wa1c011w8Io1W9Mt0Smsb5tWs8wCCV7rgBLDIb51GyvPKb928YXuRaRNXeh5tGRXqwEz2zWNvvsjo7i2iG4Bg10Yt8WlPEZaLMBT0fOcKeHXK3PUk24Nd2QTc0eT1jATWxPgALle7ytGToQNIKg5wTEn0kUOmNr9ODmtL0N6MX5ayqozMiD50AKDgs2Dp1R";

        // when & then
        assertThatThrownBy(() -> new PartnerCompany("goodchoice.kr", overflowName))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(PARTNER_COMPANY_NAME_ERROR.getMessage());
    }
}