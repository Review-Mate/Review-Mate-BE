package com.somartreview.reviewmate.domain.PartnerManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerManagerRepository extends JpaRepository<PartnerManager, Long> {

    boolean existsById(Long id);
}
