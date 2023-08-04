package com.somartreview.reviewmate.domain.travelProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleTravelProductRepository extends JpaRepository<SingleTravelProduct, Long> {
}
