package com.somartreview.reviewmate.domain.review;

import com.somartreview.reviewmate.dto.review.ReviewTagStatisticsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {


    List<Review> findAllByReservation_TravelProduct_PartnerCompany_PartnerDomainAndReservation_TravelProduct_PartnerCustomId(String partnerDomain, String partnerCustomId);

    List<Review> findAllByReservation_TravelProduct_Id(long singleTravelProductId);

    @Query("SELECT new com.somartreview.reviewmate.dto.review.ReviewTagStatisticsDto(rt.reviewProperty, rt.polarity, COUNT(rt)) " +
            "FROM ReviewTag rt " +
            "JOIN Review r ON rt.review.id = r.id " +
            "WHERE rt.review.reservation.travelProduct.id = :travelProductId " +
            "GROUP BY rt.keyword, rt.reviewProperty")
    List<ReviewTagStatisticsDto> findReviewTagStatisticsByTravelProductId(Long travelProductId);
}
