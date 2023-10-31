package com.somartreview.reviewmate.domain.review;

import com.somartreview.reviewmate.dto.review.ReviewTagStatisticsDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends ReviewJpaRepository, ReviewCustomRepository {

    boolean existsByReservation_Id(Long reservationId);

    List<Review> findAllByReservation_TravelProduct_PartnerCompany_PartnerDomainAndReservation_TravelProduct_PartnerCustomId(String partnerDomain, String partnerCustomId);

    List<ReviewTagStatisticsDto> findReviewTagStatisticsByTravelProductId(Long travelProductId);

    Optional<Review> findByReservation_Id(Long reservationId);

    List<Long> findReviewIdsAllByReservationIdsInQuery(List<Long> reservationsIds);

    @Query("select count(r.id) from Review r join r.reservation.travelProduct.partnerCompany c " +
            "where r.reservation.travelProduct.partnerCompany.partnerDomain = :partnerDomain")
    Long countByPartnerDomain(String partnerDomain);
}
