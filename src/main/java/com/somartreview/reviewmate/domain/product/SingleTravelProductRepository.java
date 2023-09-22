package com.somartreview.reviewmate.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SingleTravelProductRepository extends JpaRepository<SingleTravelProduct, Long> {

    boolean existsByPartnerCompany_PartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId);

    Optional<SingleTravelProduct> findByPartnerCompany_PartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId);

    List<SingleTravelProduct> findAllByPartnerCompany_PartnerDomainAndSingleTravelProductCategory(String partnerDomain, SingleTravelProductCategory singleTravelProductCategory);

    List<SingleTravelProduct> findAllByPartnerSeller_Id(Long partnerSellerId);
}
