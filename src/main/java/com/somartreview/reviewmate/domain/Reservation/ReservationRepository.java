package com.somartreview.reviewmate.domain.Reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByCustomer_Id(Long customerId);

    List<Reservation> findByTravelProduct_Id(Long travelProductId);
}
