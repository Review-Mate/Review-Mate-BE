package com.somartreview.reviewmate.domain.customer;

import com.somartreview.reviewmate.domain.partner.company.PartnerCompany;
import com.somartreview.reviewmate.domain.partner.company.PartnerCompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PartnerCompanyRepository partnerCompanyRepository;

    private Customer.CustomerBuilder customer = Customer.builder()
            .partnerCustomId("CUSTOMER_REPOSITORY_TEST_CUSTOMER_0001")
            .name("권순찬")
            .phoneNumber("01012345678")
            .kakaoId("sckwon770");
    PartnerCompany partnerCompany = PartnerCompany.builder()
            .partnerDomain("CustomerRepositoryTest.yanolja.com")
            .name("야놀자")
            .build();


    @Test
    void 파트너사_도메인과_파트너_커스텀_Id로_고객을_확인한다() {
        // given
        PartnerCompany savedPartnerCompany = partnerCompanyRepository.save(partnerCompany);
        Customer savedCustomer = customerRepository.save(customer.partnerCompany(savedPartnerCompany).build());
        String customerPartnerDomain = savedCustomer.getPartnerCompany().getPartnerDomain();
        String customerPartnerCustomId = savedCustomer.getPartnerCustomId();


        // when
        boolean existsCustomer = customerRepository.existsByPartnerCompany_PartnerDomainAndPartnerCustomId(
                customerPartnerDomain,
                customerPartnerCustomId
        );

        // then
        assertThat(existsCustomer).isTrue();
    }

    @Test
    void 고객_전화번호로_고객을_확인한다() {
        // given
        PartnerCompany savedPartnerCompany = partnerCompanyRepository.save(partnerCompany);
        Customer savedCustomer = customerRepository.save(customer.partnerCompany(savedPartnerCompany).build());
        String customerPhoneNumber = savedCustomer.getPhoneNumber();

        // when
        boolean exitsCustomer = customerRepository.existsByPhoneNumber(customerPhoneNumber);

        // then
        assertThat(exitsCustomer).isTrue();
    }

    @Test
    void 카톡_Id로_고객을_확인한다() {
        // given
        PartnerCompany savedPartnerCompany = partnerCompanyRepository.save(partnerCompany);
        Customer savedCustomer = customerRepository.save(customer.partnerCompany(savedPartnerCompany).build());
        String customerKakaoId = savedCustomer.getKakaoId();

        // when
        boolean exitsCustomer = customerRepository.existsByKakaoId(customerKakaoId);

        // then
        assertThat(exitsCustomer).isTrue();
    }

    @Test
    void 파트너사_도메인과_파트너_커스텀_Id로_존재하는_않는_고객을_조회한다() {
        // given
        PartnerCompany savedPartnerCompany = partnerCompanyRepository.save(partnerCompany);
        Customer savedCustomer = customerRepository.save(customer.partnerCompany(savedPartnerCompany).build());
        String customerPartnerDomain = savedCustomer.getPartnerCompany().getPartnerDomain();
        String customerPartnerCustomId = savedCustomer.getPartnerCustomId();


        // when
        Optional<Customer> findCustomer = customerRepository.findByPartnerCompany_PartnerDomainAndPartnerCustomId(
                customerPartnerDomain,
                customerPartnerCustomId
        );

        // then
        assertThat(findCustomer).isPresent();
    }

    @Test
    void 파트너사_도메인과_파트너_커스텀_Id로_고객을_삭제한다() {
        // given
        PartnerCompany savedPartnerCompany = partnerCompanyRepository.save(partnerCompany);
        Customer savedCustomer = customerRepository.save(customer.partnerCompany(savedPartnerCompany).build());
        Long customerId = savedCustomer.getId();
        String customerPartnerDomain = savedCustomer.getPartnerCompany().getPartnerDomain();
        String customerPartnerCustomId = savedCustomer.getPartnerCustomId();

        // when
        customerRepository.deleteByPartnerCompany_PartnerDomainAndPartnerCustomId(
                customerPartnerDomain,
                customerPartnerCustomId
        );

        // then
        assertThat(customerRepository.existsById(customerId)).isFalse();
    }
}