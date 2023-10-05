package com.somartreview.reviewmate.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TravelProductRepository extends JpaRepository<TravelProduct, Long> {

    boolean existsByPartnerCompany_PartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId);

    Optional<TravelProduct> findByPartnerCompany_PartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId);

    void deleteAllByPartnerCompany_PartnerDomain(String partnerDomain);


    List<TravelProduct> findAllByPartnerSeller_Id(Long partnerSellerId);
}
