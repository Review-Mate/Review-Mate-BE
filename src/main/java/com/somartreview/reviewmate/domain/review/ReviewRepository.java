package com.somartreview.reviewmate.domain.review;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReviewRepository extends ReviewJpaRepository, ReviewCustomRepository {

    Long findIdByReservationId(Long reservationId);

    @Query("select count(r.id) from Review r join r.reservation.travelProduct.partnerCompany c " +
            "where r.reservation.travelProduct.partnerCompany.partnerDomain = :partnerDomain")
    Long countByPartnerDomain(String partnerDomain);

    @Transactional
    @Modifying
    @Query("delete from Review r where r.id in :ids")
    void deleteAllByIdsInQuery(List<Long> ids);
}
