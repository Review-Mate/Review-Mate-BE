package com.somartreview.reviewmate.domain.partner.manager;


import com.somartreview.reviewmate.domain.partner.company.PartnerCompany;
import com.somartreview.reviewmate.dto.partner.manager.PartnerManagerUpdateRequest;
import com.somartreview.reviewmate.exception.DomainLogicException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.somartreview.reviewmate.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PartnerManagerTest {

    private PartnerManager partnerManager;

    @BeforeEach
    void setup() {
        partnerManager = PartnerManager.builder()
                .name("권순찬")
                .email("sckwon770@gmail.com")
                .password("123456789")
                .partnerCompany(new PartnerCompany())
                .build();
    }

    @Test
    void 파트너사_관리자의_정보를_수정한다() {
        // given
        PartnerManagerUpdateRequest partnerManagerUpdateRequest = new PartnerManagerUpdateRequest("장현우", "changhw7@gmail.com", "987654321");

        // when
        partnerManager.update(partnerManagerUpdateRequest);

        // then
        assertThat(partnerManager)
                .extracting("name", "email", "password")
                .containsExactly("장현우", "changhw7@gmail.com", "987654321");
    }

    @Test
    void 파트너사_관리자의_이름이_공백이어선_안된다() {
        // given
        String emptyName = " ";

        // when & then
        assertThatThrownBy(() -> new PartnerManager(emptyName, "changhw7@gmail.com", "987654321", new PartnerCompany()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(PARTNER_MANAGER_NAME_ERROR.getMessage());
    }

    @Test
    void 파트너사_관리자의_이름이_255자_보다_갈면_안된다() {
        // given
        String overflowName = "WTy85YZG2HsEOMahKZERNJmZrY4XHnOwugREhMcgSxYrqWnqJa9yGgBLojDzLF8l6Wa1c011w8Io1W9Mt0Smsb5tWs8wCCV7rgBLDIb51GyvPKb928YXuRaRNXeh5tGRXqwEz2zWNvvsjo7i2iG4Bg10Yt8WlPEZaLMBT0fOcKeHXK3PUk24Nd2QTc0eT1jATWxPgALle7ytGToQNIKg5wTEn0kUOmNr9ODmtL0N6MX5ayqozMiD50AKDgs2Dp1R";

        // when & then
        assertThatThrownBy(() -> new PartnerManager(overflowName, "changhw7@gmail.com", "987654321", new PartnerCompany()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(PARTNER_MANAGER_NAME_ERROR.getMessage());
    }

    @Test
    void 파트너사_관리자의_이메일은_이메일_형식이어야_한다() {
        // given
        String invalidEmail = "changhw7gmail.com";

        // when & then
        assertThatThrownBy(() -> new PartnerManager("장현우", invalidEmail, "987654321", new PartnerCompany()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(PARTNER_MANAGER_EMAIL_ERROR.getMessage());
    }

    @Test
    void 파트너사_관리자의_비밀번호는_8자_이상이어야_한다() {
        // given
        String invalidPassword = "1234567";

        // when & then
        assertThatThrownBy(() -> new PartnerManager("장현우", "changhw7@gmail.com", invalidPassword, new PartnerCompany()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(PARTNER_MANAGER_PASSWORD_ERROR.getMessage());
    }
}