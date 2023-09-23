package com.somartreview.reviewmate.domain.review;

import com.somartreview.reviewmate.dto.review.ReviewTagStatisticsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ReviewJpaRepository extends JpaRepository<Review, Long> {

    boolean existsByReservation_Id(Long reservationId);

    List<Review> findAllByReservation_TravelProduct_PartnerCompany_PartnerDomainAndReservation_TravelProduct_PartnerCustomId(String partnerDomain, String partnerCustomId);

    @Query("SELECT new com.somartreview.reviewmate.dto.review.ReviewTagStatisticsDto(rt.reviewProperty, rt.polarity, COUNT(rt)) " +
            "FROM ReviewTag rt " +
            "JOIN Review r ON rt.review.id = r.id " +
            "WHERE rt.review.reservation.travelProduct.id = :travelProductId " +
            "GROUP BY rt.keyword, rt.reviewProperty")
    List<ReviewTagStatisticsDto> findReviewTagStatisticsByTravelProductId(Long travelProductId);

    Optional<Review> findByReservation_Id(Long reservationId);
}
