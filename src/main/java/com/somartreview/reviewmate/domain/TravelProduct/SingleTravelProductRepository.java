package com.somartreview.reviewmate.domain.TravelProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SingleTravelProductRepository extends JpaRepository<SingleTravelProduct, Long> {

    boolean existsByTravelProductId(TravelProductId travelProductId);

    Optional<SingleTravelProduct> findByTravelProductId(TravelProductId travelProductId);

    List<SingleTravelProduct> findAllByTravelProductId_PartnerDomainAndTravelProductCategory(String partnerDomain, TravelProductCategory travelProductCategory);

    void deleteByTravelProductId(TravelProductId travelProductId);
}
