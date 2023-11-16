package com.somartreview.reviewmate.domain.reservation;

import com.somartreview.reviewmate.domain.product.SingleTravelProductCategory;
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


    @Query("select r from Reservation r " +
            "where r.travelProduct.partnerCompany.partnerDomain = :partnerDomain and r.partnerCustomId = :partnerCustomId")
    Optional<Reservation> findByPartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId);


    @EntityGraph(attributePaths = {"customer", "travelProduct"})
    @Query("select r from Reservation r " +
            "where r.id = :id")
    Optional<Reservation> findByIdFetchJoin(Long id);


    @EntityGraph(attributePaths = {"customer", "travelProduct"})
    @Query("select r from Reservation r " +
            "where r.customer.id = :customerId")
    List<Reservation> findAllByCustomerIdFetchJoin(Long customerId);


    @EntityGraph(attributePaths = {"customer", "travelProduct"})
    @Query("select r from Reservation r " +
            "where r.travelProduct.id = :travelProductId")
    List<Reservation> findAllByTravelProductIdFetchJoin(Long travelProductId);


    List<Reservation> findAllByTravelProductId(Long travelProductId);


    @EntityGraph(attributePaths = {"customer", "travelProduct"})
    @Query("select r from Reservation r " +
            "where r.travelProduct.id = :travelProductId and r.customer.id = :customerId")
    List<Reservation> findAllByTravelProductIdAndCustomerIdFetchJoin(Long travelProductId, Long customerId);


    @Query("select r from Reservation r join r.travelProduct.partnerCompany c " +
            "where r.travelProduct.partnerCompany.partnerDomain = :partnerDomain and r.createdAt >= :dateTime")
    List<Reservation> findAllByCreatedAtGreaterThanEqual(String partnerDomain, LocalDateTime dateTime);


    @Query("select r from Reservation r join r.travelProduct.partnerCompany c " +
            "left outer join SingleTravelProduct p on r.travelProduct.id = p.id " +
            "where c.partnerDomain = :partnerDomain and p.singleTravelProductCategory = :category and r.createdAt between :startDateTime and :endDateTime")
    List<Reservation> findAllByCategoryAndCreatedAtBetween(String partnerDomain, SingleTravelProductCategory category, LocalDateTime startDateTime, LocalDateTime endDateTime);


    @Query("select r from Reservation r join r.travelProduct.partnerCompany c " +
            "where r.travelProduct.partnerCompany.partnerDomain = :partnerDomain and r.createdAt between :startDateTime and :endDateTime")
    List<Reservation> findAllByCreatedAtBetween(String partnerDomain, LocalDateTime startDateTime, LocalDateTime endDateTime);


    @Query("select r from Reservation r inner join fetch r.customer c " +
            "where r.travelProduct.id = :productId and r.startDateTime = :localDateTime")
    List<Reservation> findByProductIdAndStartDateTime(Long productId, LocalDateTime localDateTime);

    @Transactional
    @Modifying
    @Query("delete from Reservation r where r.id = :id")
    void deleteById(Long id);


    @Transactional
    @Modifying
    @Query("delete from Reservation r where r.travelProduct.id = :travelProductId")
    void deleteAllByTravelProductId(Long travelProductId);
}