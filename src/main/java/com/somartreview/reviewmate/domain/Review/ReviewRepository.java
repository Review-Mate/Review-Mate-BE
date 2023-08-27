package com.somartreview.reviewmate.domain.Review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByIdAndTravelProduct_PartnerCompany_PartnerDomain(Long id, String partnerDomain);

    List<Review> findAllByTravelProduct_PartnerCompany_PartnerDomainAndTravelProduct_PartnerCustomId(String partnerDomain, String partnerCustomId);
}
