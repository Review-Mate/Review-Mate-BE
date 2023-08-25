package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.Customer.Customer;
import com.somartreview.reviewmate.domain.Reservation.Reservation;
import com.somartreview.reviewmate.domain.Reservation.ReservationRepository;
import com.somartreview.reviewmate.domain.TravelProduct.SingleTravelProduct;
import com.somartreview.reviewmate.domain.TravelProduct.TravelProduct;
import com.somartreview.reviewmate.dto.request.customer.CustomerCreateRequest;
import com.somartreview.reviewmate.dto.request.customer.CustomerIdDto;
import com.somartreview.reviewmate.dto.request.travelProduct.SingleTravelProductCreateRequest;
import com.somartreview.reviewmate.dto.request.travelProduct.TravelProductIdDto;
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
        final Customer customer = customerService.create(partnerDomain, customerCreateRequest);
        final SingleTravelProduct travelProduct = singleTravelProductService.create(partnerDomain, singleTravelProductCreateRequest, thumbnail);

        return reservationRepository.save(new Reservation(customer, travelProduct)).getId();
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(RESERVATION_NOT_FOUND));
    }

    public List<Reservation> findAllByTravelProductId(TravelProductIdDto travelProductIdDto) {
        travelProductService.validateExistTravelProduct(travelProductIdDto);

        return reservationRepository.findAllByTravelProduct_TravelProductId(travelProductIdDto.toEntity());
    }

    public List<Reservation> findAllByCustomerId(CustomerIdDto customerIdDto) {
        customerService.validateExistCustomer(customerIdDto);

        return reservationRepository.findAllByCustomer_CustomerId(customerIdDto.toEntity());
    }

    public SingleTravelProductReservationResponse getSingleTravelProductReservationResponseById(Long id) {
        Reservation reservation = findById(id);

        return new SingleTravelProductReservationResponse(reservation);
    }

    public List<SingleTravelProductReservationResponse> getSingleTravelProductReservationResponseByCustomerOrSingleTravelProduct(String partnerDomain,
                                                                                                                                 String customerPartnerCustomId,
                                                                                                                                 String travelProductPartnerCustomId) {
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
