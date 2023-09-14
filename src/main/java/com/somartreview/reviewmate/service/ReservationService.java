package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.customer.Customer;
import com.somartreview.reviewmate.domain.reservation.Reservation;
import com.somartreview.reviewmate.domain.reservation.ReservationRepository;
import com.somartreview.reviewmate.domain.product.SingleTravelProduct;
import com.somartreview.reviewmate.dto.reservation.SingleTravelReservationCreateRequest;
import com.somartreview.reviewmate.dto.reservation.SingleTravelProductReservationResponse;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.service.live.LiveFeedbackService;
import com.somartreview.reviewmate.service.live.LiveSatisfactionService;
import com.somartreview.reviewmate.service.products.SingleTravelProductService;
import com.somartreview.reviewmate.service.products.TravelProductService;
import com.somartreview.reviewmate.service.review.ReviewService;
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
    private final LiveSatisfactionService liveSatisfactionService;
    private final LiveFeedbackService liveFeedbackService;
    private final ReviewService reviewService;

    @Transactional
    public Long createSingleTravelProductReservation(final Customer customer,
                                                     final SingleTravelProduct singleTravelProduct,
                                                     SingleTravelReservationCreateRequest singleTravelReservationCreateRequest) {

        return reservationRepository.save(singleTravelReservationCreateRequest.toEntity(customer, singleTravelProduct)).getId();
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
        return reservationRepository.findAllByTravelProduct_Id(travelProductId);
    }

    public List<Reservation> findAllByCustomerId(Long customerId) {
        return reservationRepository.findAllByCustomer_Id(customerId);
    }

    public List<Reservation> findAllByTravelProductIdAndCustomerId(Long travelProductId, Long customerId) {
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

        liveSatisfactionService.deleteByReservationId(reservationId);
        liveFeedbackService.deleteByReservationId(reservationId);
        reviewService.deleteByReservationId(reservationId);
        reservationRepository.deleteById(reservationId);
    }

    public void validateExistReservation(Long reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            throw new DomainLogicException(RESERVATION_NOT_FOUND);
        }
    }

    @Transactional
    public void deleteAllByCustomerId(Long customerId) {
        reservationRepository.findAllByCustomer_Id(customerId).forEach(reservation -> {
            deleteByReservationId(reservation.getId());
        });
    }

    @Transactional
    public void deleteAllByTravelProductId(Long travelProductId) {
        reservationRepository.findAllByTravelProduct_Id(travelProductId).forEach(reservation -> {
            deleteByReservationId(reservation.getId());
        });
    }
}
