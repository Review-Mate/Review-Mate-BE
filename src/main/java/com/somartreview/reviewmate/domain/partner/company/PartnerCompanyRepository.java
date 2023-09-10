package com.somartreview.reviewmate.domain.partner.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerCompanyRepository extends JpaRepository<PartnerCompany, Long> {

    boolean existsByPartnerDomain(String partnerDomain);

    Optional<PartnerCompany> findByPartnerDomain(String partnerDomain);

    void deleteByPartnerDomain(String partnerDomain);
}
