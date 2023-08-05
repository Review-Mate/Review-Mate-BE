package com.somartreview.reviewmate.domain.PartnerCompany;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerManagerRepository extends JpaRepository<PartnerManager, Long> {
}
