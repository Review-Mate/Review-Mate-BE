package com.somartreview.reviewmate.domain.Review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByTravelProduct_Id(Long travelProductId);

    List<Review> findAllByTravelProduct_Id(Long travelProductId);
}
