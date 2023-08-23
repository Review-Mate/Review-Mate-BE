package com.somartreview.reviewmate.domain.PartnerSeller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerSellerRepository extends JpaRepository<PartnerSeller, Long> {

    Optional<PartnerSeller> findByPhoneNumber(String phoneNumber);

    void deleteByPhoneNumber(String phoneNumber);
}
