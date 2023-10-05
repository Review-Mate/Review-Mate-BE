package com.somartreview.reviewmate.domain.reservation;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByTravelProduct_PartnerCompany_PartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId);

    List<Reservation> findAll();

    @EntityGraph(attributePaths = {"customer", "travelProduct"})
    @Query("select r from Reservation r where r.id = :id")
    Optional<Reservation> findByIdFetchJoin(Long id);

    @EntityGraph(attributePaths = {"customer", "travelProduct"})
    @Query("select r from Reservation r where r.customer.id = :customerId")
    List<Reservation> findAllByCustomerIdFetchJoin(Long customerId);

    List<Reservation> findAllByCustomerId(Long customerId);


    @EntityGraph(attributePaths = {"customer", "travelProduct"})
    @Query("select r from Reservation r where r.travelProduct.id = :travelProductId")
    List<Reservation> findAllByTravelProductIdFetchJoin(Long travelProductId);

    List<Reservation> findAllByTravelProductId(Long travelProductId);


    @EntityGraph(attributePaths = {"customer", "travelProduct"})
    @Query("select r from Reservation r where r.travelProduct.id = :travelProductId and r.customer.id = :customerId")
    List<Reservation> findAllByTravelProductIdAndCustomerIdFetchJoin(Long travelProductId, Long customerId);

}