package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.reservation.ReservationRepository;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.service.live.LiveFeedbackDeleteService;
import com.somartreview.reviewmate.service.live.LiveSatisfactionDeleteService;
import com.somartreview.reviewmate.service.review.ReviewDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.somartreview.reviewmate.exception.ErrorCode.RESERVATION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ReservationDeleteService {

    private final ReservationRepository reservationRepository;
    private final LiveSatisfactionDeleteService liveSatisfactionService;
    private final LiveFeedbackDeleteService liveFeedbackService;
    private final ReviewDeleteService reviewService;


    @Transactional
    public void delete(Long id) {
        validateExistReservation(id);

        liveSatisfactionService.deleteByReservationId(id);
        liveFeedbackService.deleteByReservationId(id);
        reviewService.deleteByReservationId(id);
        reservationRepository.deleteById(id);
    }

    private void validateExistReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new DomainLogicException(RESERVATION_NOT_FOUND);
        }
    }

    @Transactional
    public void deleteAllByCustomerId(Long customerId) {
        reservationRepository.findAllByCustomer_Id(customerId).forEach(reservation -> {
            delete(reservation.getId());
        });
    }

    @Transactional
    public void deleteAllByTravelProductId(Long travelProductId) {
        reservationRepository.findAllByTravelProduct_Id(travelProductId).forEach(reservation -> {
            delete(reservation.getId());
        });
    }
}
