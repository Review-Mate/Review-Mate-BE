package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.Customer.Customer;
import com.somartreview.reviewmate.domain.Reservation.Reservation;
import com.somartreview.reviewmate.domain.Reservation.ReservationRepository;
import com.somartreview.reviewmate.domain.TravelProduct.TravelProduct;
import com.somartreview.reviewmate.dto.response.reservation.SingleTravelProductReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerService customerService;
    private final TravelProductService travelProductService;

    @Transactional
    public Long createReservation(Long customerId, String partnerDomain, Long travelProductId) {
        final Customer customer = customerService.findCustomerById(customerId);
        final TravelProduct travelProduct = travelProductService.findTravelProductByPartnerDomainAndTravelProductId(partnerDomain, travelProductId);

        return reservationRepository.save(new Reservation(customer, travelProduct)).getId();
    }

    public List<SingleTravelProductReservationResponse> getSingleTravelProductReservationsByTravelProductId(Long travelProductId) {
        return reservationRepository.findByTravelProduct_Id(travelProductId)
                .stream()
                .map(SingleTravelProductReservationResponse::new)
                .toList();
    }

    public List<SingleTravelProductReservationResponse> getSingleTravelProductReservationsByCustomerId(Long customerId) {
        return reservationRepository.findByCustomer_Id(customerId)
                .stream()
                .map(SingleTravelProductReservationResponse::new)
                .toList();
    }

    @Transactional
    public void deleteReservationById(Long id) {
        reservationRepository.deleteById(id);
    }
}
