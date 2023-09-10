package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.customer.Customer;
import com.somartreview.reviewmate.domain.reservation.Reservation;
import com.somartreview.reviewmate.domain.reservation.ReservationRepository;
import com.somartreview.reviewmate.domain.product.SingleTravelProduct;
import com.somartreview.reviewmate.dto.reservation.SingleTravelReservationCreateRequest;
import com.somartreview.reviewmate.dto.reservation.SingleTravelProductReservationResponse;
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
                                                     SingleTravelReservationCreateRequest singleTravelReservationCreateRequest,
                                                     MultipartFile thumbnail) {
        final Customer customer = customerService.retreiveCustomer(partnerDomain, singleTravelReservationCreateRequest.getCustomerCreateRequest());
        final SingleTravelProduct travelProduct = singleTravelProductService.retreiveSingleTravelProduct(partnerDomain, singleTravelReservationCreateRequest.getSingleTravelProductCreateRequest(), thumbnail);

        return reservationRepository.save(singleTravelReservationCreateRequest.toEntity(customer, travelProduct)).getId();
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(RESERVATION_NOT_FOUND));
    }

    public Reservation findByPartnerDomainAndPartnerCustomId(String partnerDomain, String partnerCustomId) {
        return reservationRepository.findByTravelProduct_PartnerCompany_PartnerDomainAndPartnerCustomId(partnerDomain, partnerCustomId)
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

    public List<Reservation> findAllByTravelProductIdAndCustomerId(Long travelProductId, Long customerId) {
        travelProductService.validateExistTravelProduct(travelProductId);
        customerService.validateExistCustomer(customerId);

        return reservationRepository.findAllByTravelProduct_IdAndCustomer_Id(travelProductId, customerId);
    }

    public SingleTravelProductReservationResponse getSingleTravelProductReservationResponseById(Long id) {
        Reservation reservation = findById(id);

        return new SingleTravelProductReservationResponse(reservation);
    }

    public List<SingleTravelProductReservationResponse> getSingleTravelProductReservationResponseByCustomerOrSingleTravelProduct(Long customerId, Long singleTravelProductId) {
        if (singleTravelProductId == null) {
            return findAllByCustomerId(customerId).stream()
                    .map(SingleTravelProductReservationResponse::new)
                    .toList();
        } else if (customerId == null) {
            return findAllByTravelProductId(singleTravelProductId).stream()
                    .map(SingleTravelProductReservationResponse::new)
                    .toList();
        } else {
            return findAllByTravelProductIdAndCustomerId(singleTravelProductId, customerId).stream()
                    .map(SingleTravelProductReservationResponse::new)
                    .toList();
        }
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
