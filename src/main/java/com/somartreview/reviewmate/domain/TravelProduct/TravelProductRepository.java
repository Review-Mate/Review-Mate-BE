package com.somartreview.reviewmate.domain.TravelProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TravelProductRepository extends JpaRepository<TravelProduct, Long> {

    boolean existsByPartnerTravelProductId(String partnerTravelProductId);

    Integer countByPartnerTravelProductId(String partnerTravelProductId);

    Optional<TravelProduct> findByPartnerTravelProductId(String partnerTravelProductId);

    List<TravelProduct> findByCategory(Category category);
}
