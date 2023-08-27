package com.somartreview.reviewmate.domain.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByPartnerCompany_PartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByKakaoId(String kakaoId);

    Optional<Customer> findByPartnerCompany_PartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId);

    void deleteByPartnerCompany_PartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId);
}
