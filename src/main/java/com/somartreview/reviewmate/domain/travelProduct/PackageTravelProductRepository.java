package com.somartreview.reviewmate.domain.travelProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageTravelProductRepository extends JpaRepository<PackageTravelProduct, Long> {
}
