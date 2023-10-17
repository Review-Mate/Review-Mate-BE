package com.somartreview.reviewmate.domain.product;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SingleTravelProductRepository extends JpaRepository<SingleTravelProduct, Long> {

    boolean existsByPartnerCompany_PartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId);

    @Query("select p from SingleTravelProduct p " +
            "where p.partnerCompany.partnerDomain = :partnerDomain and p.partnerCustomId = :partnerCustomId")
    Optional<SingleTravelProduct> findByPartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId);

    @EntityGraph(attributePaths = {"partnerSeller"})
    @Query("select p from SingleTravelProduct p " +
            "where p.partnerCompany.partnerDomain = :partnerDomain and p.partnerCustomId = :partnerCustomId")
    Optional<SingleTravelProduct> findByPartnerDomainAndPartnerCustomIdFetchJoin(String partnerDomain, String partnerCustomId);

    @EntityGraph(attributePaths = {"partnerSeller"})
    @Query("select p from SingleTravelProduct p " +
            "where p.partnerCompany.partnerDomain = :partnerDomain and p.singleTravelProductCategory = :singleTravelProductCategory")
    List<SingleTravelProduct> findAllByPartnerDomainAndSingleTravelProductCategoryFetchJoin(String partnerDomain, SingleTravelProductCategory singleTravelProductCategory);

    boolean existsByPartnerCompany_PartnerDomainAndPartnerCustomIdAndPartnerSeller_Id(String partnerDomain, String partnerCustomId, Long partnerSellerId);
}
