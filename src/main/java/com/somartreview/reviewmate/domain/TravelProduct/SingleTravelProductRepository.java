package com.somartreview.reviewmate.domain.TravelProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SingleTravelProductRepository extends JpaRepository<SingleTravelProduct, Long> {

    boolean existsByPartnerCompany_PartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId);

    Optional<SingleTravelProduct> findByPartnerCompany_PartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId);

    List<SingleTravelProduct> findAllByPartnerCompany_PartnerDomainAndTravelProductCategory(String partnerDomain, TravelProductCategory travelProductCategory);
}
