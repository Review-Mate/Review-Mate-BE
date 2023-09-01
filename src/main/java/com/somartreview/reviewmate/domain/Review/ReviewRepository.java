package com.somartreview.reviewmate.domain.Review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {


    List<Review> findAllByReservation_TravelProduct_PartnerCompany_PartnerDomainAndReservation_TravelProduct_PartnerCustomId(String partnerDomain, String partnerCustomId);
}
