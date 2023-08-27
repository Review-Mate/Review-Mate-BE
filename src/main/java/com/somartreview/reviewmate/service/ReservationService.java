package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.Customer.Customer;
import com.somartreview.reviewmate.domain.Reservation.Reservation;
import com.somartreview.reviewmate.domain.Reservation.ReservationRepository;
import com.somartreview.reviewmate.domain.TravelProduct.SingleTravelProduct;
import com.somartreview.reviewmate.dto.request.customer.CustomerCreateRequest;
import com.somartreview.reviewmate.dto.request.travelProduct.SingleTravelProductCreateRequest;
import com.somartreview.reviewmate.dto.response.reservation.SingleTravelProductReservationResponse;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.service.products.SingleTravelProductService;
import com.somartreview.reviewmate.service.products.TravelProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.somartreview.reviewmate.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerService customerService;
    private final TravelProductService travelProductService;
    private final SingleTravelProductService singleTravelProductService;

    @Transactional
    public Long createSingleTravelProductReservation(String partnerDomain,
                                                     CustomerCreateRequest customerCreateRequest,
                                                     SingleTravelProductCreateRequest singleTravelProductCreateRequest, MultipartFile thumbnail) {
        final Customer customer = customerService.retreiveCustomer(partnerDomain, customerCreateRequest);
        final SingleTravelProduct travelProduct = singleTravelProductService.retreiveSingleTravelProduct(partnerDomain, singleTravelProductCreateRequest, thumbnail);

        return reservationRepository.save(new Reservation(customer, travelProduct)).getId();
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(RESERVATION_NOT_FOUND));
    }

    public List<Reservation> findAllByTravelProductId(Long travelProductId) {
        travelProductService.validateExistTravelProduct(travelProductId);

        return reservationRepository.findAllByTravelProduct_Id(travelProductId);
    }

    public List<Reservation> findAllByCustomerId(Long customerId) {
        customerService.validateExistCustomer(customerId);

        return reservationRepository.findAllByCustomer_Id(customerId);
    }

    public SingleTravelProductReservationResponse getSingleTravelProductReservationResponseById(Long id) {
        Reservation reservation = findById(id);

        return new SingleTravelProductReservationResponse(reservation);
    }

    public List<SingleTravelProductReservationResponse> getSingleTravelProductReservationResponseByCustomerOrSingleTravelProduct(Long customerId, Long singleTravelProductId) {
        return reservationRepository.findAll().stream()
                .map(SingleTravelProductReservationResponse::new)
                .toList();
    }

    @Transactional
    public void deleteByReservationId(Long reservationId) {
        validateExistReservation(reservationId);

        reservationRepository.deleteById(reservationId);
    }

    public void validateExistReservation(Long reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            throw new DomainLogicException(RESERVATION_NOT_FOUND);
        }
    }
}
