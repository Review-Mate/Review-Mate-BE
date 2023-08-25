package com.somartreview.reviewmate.domain.Review;

import com.somartreview.reviewmate.domain.TravelProduct.TravelProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByIdAndTravelProduct_PartnerCompany_Domain(Long id, String partnerDomain);

    List<Review> findAllByTravelProduct_TravelProductId(TravelProductId travelProductId);
}
