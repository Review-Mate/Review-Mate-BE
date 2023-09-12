package com.somartreview.reviewmate.domain.customer;

import com.somartreview.reviewmate.domain.partner.company.PartnerCompany;
import com.somartreview.reviewmate.dto.customer.CustomerUpdateRequest;
import com.somartreview.reviewmate.exception.DomainLogicException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.somartreview.reviewmate.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

    private Customer customer;



    @BeforeEach
    void setup() {
        customer = Customer.builder()
                .partnerCustomId("CUSTOMER_TEST_CUSTOMER_0001")
                .name("권순찬")
                .phoneNumber("01012345678")
                .kakaoId("sckwon770")
                .build();
    }

    @Test
    void 고객의_정보를_수정한다() {
        // given
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest("장현우", "01098765432", "changhw7");

        // when
        customer.update(customerUpdateRequest);

        // then
        assertThat(customer)
                .extracting("name", "phoneNumber", "kakaoId")
                .containsExactly("장현우", "01098765432", "changhw7");
    }

    @Test
    void 고객의_커스텀_Id는_공백이어선_안된다() {
        // given
        String emptyPartnerCustomId = " ";

        // when & then
        assertThatThrownBy(() -> new Customer(emptyPartnerCustomId, "권순찬", "01012345678", "sckwon770", new PartnerCompany()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(CUSTOMER_PARTNER_CUSTOM_ID_ERROR.getMessage());
    }

    @Test
    void 고객의_커스텀_Id는_최대길이를_넘어선_안된다() {
        // given
        String overflowPartnerCustomId = "WTy85YZG2HsEOMahKZERNJmZrY4XHnOwugREhMcgSxYrqWnqJa9yGgBLojDzLF8l6Wa1c011w8Io1W9Mt0Smsb5tWs8wCCV7rgBLDIb51GyvPKb928YXuRaRNXeh5tGRXqwEz2zWNvvsjo7i2iG4Bg10Yt8WlPEZaLMBT0fOcKeHXK3PUk24Nd2QTc0eT1jATWxPgALle7ytGToQNIKg5wTEn0kUOmNr9ODmtL0N6MX5ayqozMiD50AKDgs2Dp1R";

        // when & then
        assertThatThrownBy(() -> new Customer(overflowPartnerCustomId, "권순찬", "01012345678", "sckwon770", new PartnerCompany()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(CUSTOMER_PARTNER_CUSTOM_ID_ERROR.getMessage());
    }

    @Test
    void 고객의_이름은_공백이어선_안된다() {
        // given
        String emptyName = " ";

        // when & then
        assertThatThrownBy(() -> new Customer("emptyPartnerCustomId", emptyName, "01012345678", "sckwon770", new PartnerCompany()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(CUSTOMER_NAME_ERROR.getMessage());
    }

    @Test
    void 고객의_이름은_최대길이를_넘어선_안된다() {
        // given
        String overflowName = "WTy85YZG2HsEOMahKZERNJmZrY4XHnOwugREhMcgSxYrqWnqJa9yGgBLojDzLF8l6Wa1c011w8Io1W9Mt0Smsb5tWs8wCCV7rgBLDIb51GyvPKb928YXuRaRNXeh5tGRXqwEz2zWNvvsjo7i2iG4Bg10Yt8WlPEZaLMBT0fOcKeHXK3PUk24Nd2QTc0eT1jATWxPgALle7ytGToQNIKg5wTEn0kUOmNr9ODmtL0N6MX5ayqozMiD50AKDgs2Dp1R";

        // when & then
        assertThatThrownBy(() -> new Customer("CUSTOMER_TEST_CUSTOMER_0001", overflowName, "01012345678", "sckwon770", new PartnerCompany()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(CUSTOMER_NAME_ERROR.getMessage());
    }

    @Test
    void 고객의_휴대폰_번호는_공백이어선_안된다() {
        // given
        String emptyPhoneNumber = " ";

        // when & then
        assertThatThrownBy(() -> new Customer("CUSTOMER_TEST_CUSTOMER_0001", "권순찬", emptyPhoneNumber, "sckwon770", new PartnerCompany()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(CUSTOMER_PHONE_NUMBER_ERROR.getMessage());
    }

    @Test
    void 고객의_휴대폰_번호는_숫자_11자리다() {
        // given
        String wrongPhoneNumber = "010-8799-9941";

        // when & then
        assertThatThrownBy(() -> new Customer("CUSTOMER_TEST_CUSTOMER_0001", "권순찬", wrongPhoneNumber, "sckwon770", new PartnerCompany()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(CUSTOMER_PHONE_NUMBER_ERROR.getMessage());
    }
}