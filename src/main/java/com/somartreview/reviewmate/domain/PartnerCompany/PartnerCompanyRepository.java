package com.somartreview.reviewmate.domain.PartnerCompany;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerCompanyRepository extends JpaRepository<PartnerCompany, Long> {

    boolean existsByDomain(String domain);

    Optional<PartnerCompany> findByDomain(String domain);
}
