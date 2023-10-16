package com.somartreview.reviewmate.domain.reservation;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Query("select r.review.id from Reservation r join r.travelProduct.partnerCompany c " +
            "where r.travelProduct.partnerCompany.partnerDomain = :partnerDomain and r.createdAt >= :dateTime")
    List<Long> findAllReviewFKsByCreatedAtGreaterThanEqual(String partnerDomain, LocalDateTime dateTime);

    @Transactional
    @Modifying
    @Query("delete from Reservation r where r.id = :id")
    void deleteById(Long id);

    @Transactional
    @Modifying
    @Query("delete from Reservation r where r.id in :ids")
    void deleteAllByIdsInQuery(List<Long> ids);

    @Transactional
    @Modifying
    @Query("delete from Reservation r where r.travelProduct.id = :travelProductId")
    void deleteAllByTravelProductId(Long travelProductId);
}