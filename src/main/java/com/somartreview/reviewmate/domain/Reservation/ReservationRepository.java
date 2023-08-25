package com.somartreview.reviewmate.domain.Reservation;

import com.somartreview.reviewmate.domain.Customer.CustomerId;
import com.somartreview.reviewmate.domain.TravelProduct.TravelProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAll();

    List<Reservation> findAllByCustomer_CustomerId(CustomerId customerId);

    List<Reservation> findAllByTravelProduct_TravelProductId(TravelProductId travelProductId);
}
