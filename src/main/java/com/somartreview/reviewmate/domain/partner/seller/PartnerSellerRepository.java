package com.somartreview.reviewmate.domain.partner.seller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerSellerRepository extends JpaRepository<PartnerSeller, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByKakaoId(String kakaoId);

    boolean existsByIdAndPartnerCompany_PartnerDomain(Long id, String partnerDomain);

    boolean existsByPhoneNumberAndPartnerCompany_PartnerDomain(String phoneNumber, String partnerDomain);

    Optional<PartnerSeller> findByPhoneNumber(String phoneNumber);

    void deleteByPhoneNumber(String phoneNumber);
}
