package com.somartreview.reviewmate.domain.reservation;

import com.somartreview.reviewmate.dto.reservation.SingleTravelProductReservationResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByTravelProduct_PartnerCompany_PartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId);

    List<Reservation> findAll();


    @Query("select new com.somartreview.reviewmate.dto.reservation.SingleTravelProductReservationResponse(" +
            "r.id, r.partnerCustomId, r.customer.partnerCustomId, r.customer.name, r.customer.phoneNumber, r.travelProduct.partnerCustomId, r.travelProduct.name" +
            ") from Reservation r left join r.customer left join r.travelProduct where r.customer.id = :customerId")
    List<SingleTravelProductReservationResponse> findAllByCustomerId(Long customerId);


    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    @Query("select new com.somartreview.reviewmate.dto.reservation.SingleTravelProductReservationResponse(" +
            "r.id, r.partnerCustomId, r.customer.partnerCustomId, r.customer.name, r.customer.phoneNumber, r.travelProduct.partnerCustomId, r.travelProduct.name" +
            ") from Reservation r left join r.customer left join r.travelProduct where r.travelProduct.id = :travelProductId")
    List<SingleTravelProductReservationResponse> findAllByTravelProductId(Long travelProductId);


    @Query("select new com.somartreview.reviewmate.dto.reservation.SingleTravelProductReservationResponse(" +
            "r.id, r.partnerCustomId, r.customer.partnerCustomId, r.customer.name, r.customer.phoneNumber, r.travelProduct.partnerCustomId, r.travelProduct.name" +
            ") from Reservation r left join r.customer left join r.travelProduct where r.travelProduct.id = :travelProductId and r.customer.id = :customerId")
    List<SingleTravelProductReservationResponse> findAllByTravelProductIdAndCustomerId(Long travelProductId, Long customerId);

}