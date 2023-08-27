package com.somartreview.reviewmate.domain.Reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAll();

    List<Reservation> findAllByCustomer_Id(Long customerId);

    List<Reservation> findAllByTravelProduct_Id(Long travelProductId);
}
