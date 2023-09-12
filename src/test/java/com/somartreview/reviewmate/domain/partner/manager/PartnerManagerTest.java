package com.somartreview.reviewmate.domain.partner.manager;


import com.somartreview.reviewmate.domain.partner.company.PartnerCompany;
import com.somartreview.reviewmate.dto.partner.manager.PartnerManagerUpdateRequest;
import com.somartreview.reviewmate.exception.DomainLogicException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.somartreview.reviewmate.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PartnerManagerTest {

    private PartnerManager partnerManager;

    @BeforeEach
    void setup() {
        partnerManager = PartnerManager.builder()
                .role(PartnerManagerRole.ADMIN)
                .name("권순찬")
                .email("sckwon770@gmail.com")
                .password("123456789")
                .partnerCompany(new PartnerCompany())
                .build();
    }

    @Test
    void 기본_생성자로_피트너사_관리자를_생성한다() {
        new PartnerManager("권순찬", "sckwon770@gmail.com", "123456789", new PartnerCompany());

        assertThat(partnerManager)
                .extracting("name", "email", "password")
                .containsExactly("권순찬", "sckwon770@gmail.com", "123456789");
        assertThat(partnerManager.getPartnerCompany().getPartnerManagers())
                .last()
                .extracting("name")
                .isEqualTo("권순찬");
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
        name = " ";

        // when & then
        assertThatThrownBy(() -> new PartnerManager(emptyName, "changhw7@gmail.com", "987654321", new PartnerCompany()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(PARTNER_MANAGER_NAME_ERROR.getMessage());
    }

    @Test
    void 파트너사_관리자의_이름이_255자_보다_길면_안된다() {
        // given
        name = "a".repeat(256);

        // when & then
        assertThatThrownBy(() -> new PartnerManager(longName, "changhw7@gmail.com", "987654321", new PartnerCompany()))
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
    void 파트너사_관리자의_비밀번호는_7자리_이하면_안된다() {
        // given
        String invalidPassword = "1234567";

        // when & then
        assertThatThrownBy(() -> new PartnerManager("장현우", "changhw7@gmail.com", invalidPassword, new PartnerCompany()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(PARTNER_MANAGER_PASSWORD_ERROR.getMessage());
    }
}