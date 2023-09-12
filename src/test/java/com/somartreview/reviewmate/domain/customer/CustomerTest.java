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
        String partnerCustomId = " ";

        // when & then
        assertThatThrownBy(() -> new Customer(partnerCustomId, "권순찬", "01012345678", "sckwon770", new PartnerCompany()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(CUSTOMER_PARTNER_CUSTOM_ID_ERROR.getMessage());
    }

    @Test
    void 고객의_커스텀_Id는_최대길이를_넘어선_안된다() {
        // given
        String partnerCustomId = "a".repeat(256);

        // when & then
        assertThatThrownBy(() -> new Customer(partnerCustomId, "권순찬", "01012345678", "sckwon770", new PartnerCompany()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(CUSTOMER_PARTNER_CUSTOM_ID_ERROR.getMessage());
    }

    @Test
    void 고객의_이름은_공백이어선_안된다() {
        // given
        String name = " ";

        // when & then
        assertThatThrownBy(() -> new Customer("emptyPartnerCustomId", name, "01012345678", "sckwon770", new PartnerCompany()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(CUSTOMER_NAME_ERROR.getMessage());
    }

    @Test
    void 고객의_이름은_최대길이를_넘어선_안된다() {
        // given
        String name = "a".repeat(256);

        // when & then
        assertThatThrownBy(() -> new Customer("CUSTOMER_TEST_CUSTOMER_0001", name, "01012345678", "sckwon770", new PartnerCompany()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(CUSTOMER_NAME_ERROR.getMessage());
    }

    @Test
    void 고객의_휴대폰_번호는_공백이어선_안된다() {
        // given
        String phoneNumber = " ";

        // when & then
        assertThatThrownBy(() -> new Customer("CUSTOMER_TEST_CUSTOMER_0001", "권순찬", phoneNumber, "sckwon770", new PartnerCompany()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(CUSTOMER_PHONE_NUMBER_ERROR.getMessage());
    }

    @Test
    void 고객의_휴대폰_번호는_숫자로만_이루어_졌다() {
        // given
        String phoneNumber = "010-1234-5678";

        // when & then
        assertThatThrownBy(() -> new Customer("CUSTOMER_TEST_CUSTOMER_0001", "권순찬", phoneNumber, "sckwon770", new PartnerCompany()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(CUSTOMER_PHONE_NUMBER_ERROR.getMessage());
    }

    @Test
    void 고객의_휴대폰_번호는_11자리_숫자여야_한다() {
        // given
        String phoneNumber = "0101234";

        // when & then
        assertThatThrownBy(() -> new Customer("CUSTOMER_TEST_CUSTOMER_0001", "권순찬", phoneNumber, "sckwon770", new PartnerCompany()))
                .isInstanceOf(DomainLogicException.class)
                .hasMessage(CUSTOMER_PHONE_NUMBER_ERROR.getMessage());
    }
}