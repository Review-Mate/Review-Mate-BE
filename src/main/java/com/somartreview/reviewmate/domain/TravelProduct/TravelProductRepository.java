package com.somartreview.reviewmate.domain.TravelProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TravelProductRepository extends JpaRepository<TravelProduct, Long> {

    boolean existsByTravelProductId(TravelProductId travelProductId);

    Optional<TravelProduct> findByTravelProductId(TravelProductId travelProductId);
}
