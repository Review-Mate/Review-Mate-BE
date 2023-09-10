package com.somartreview.reviewmate.domain.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByTravelProduct_PartnerCompany_PartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId);

    List<Reservation> findAll();

    List<Reservation> findAllByCustomer_Id(Long customerId);

    List<Reservation> findAllByTravelProduct_Id(Long travelProductId);

    List<Reservation> findAllByTravelProduct_IdAndCustomer_Id(Long travelProductId, Long customerId);

}
