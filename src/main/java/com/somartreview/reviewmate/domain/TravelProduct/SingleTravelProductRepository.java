package com.somartreview.reviewmate.domain.TravelProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface SingleTravelProductRepository extends JpaRepository<SingleTravelProduct, Long> {

    boolean existsByPartnerTravelProductId(String partnerTravelProductId);

    Integer countByPartnerTravelProductId(String partnerTravelProductId);

    Optional<SingleTravelProduct> findByPartnerTravelProductId(String partnerTravelProductId);

    List<SingleTravelProduct> findByCategory(Category category);
}
