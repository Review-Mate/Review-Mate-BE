package com.somartreview.reviewmate.domain.PartnerSeller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerSellerRepository extends JpaRepository<PartnerSeller, Long> {

    boolean existsByIdAndPartnerCompany_Domain(Long id, String partnerDomain);

    boolean existsByPhoneNumberAndPartnerCompany_Domain(String phoneNumber, String partnerDomain);

    Optional<PartnerSeller> findByPhoneNumber(String phoneNumber);

    void deleteByPhoneNumber(String phoneNumber);
}
