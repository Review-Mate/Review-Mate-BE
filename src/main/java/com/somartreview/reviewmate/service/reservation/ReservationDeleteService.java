package com.somartreview.reviewmate.service.reservation;

import com.somartreview.reviewmate.domain.reservation.Reservation;
import com.somartreview.reviewmate.domain.reservation.ReservationRepository;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import com.somartreview.reviewmate.service.live.LiveFeedbackDeleteService;
import com.somartreview.reviewmate.service.live.LiveSatisfactionDeleteService;
import com.somartreview.reviewmate.service.review.ReviewDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationDeleteService {

    private final ReservationRepository reservationRepository;
    private final LiveSatisfactionDeleteService liveSatisfactionDeleteService;
    private final LiveFeedbackDeleteService liveFeedbackDeleteService;
    private final ReviewDeleteService reviewDeleteService;


    @Transactional
    public void delete(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(ErrorCode.RESERVATION_NOT_FOUND));
        List<Reservation> reservations = List.of(reservation);
        deleteReservationCascade(reservations);
        reservationRepository.deleteById(id);
    }


    // 문제의 원인
    @Transactional
    public void deleteAllByTravelProductId(Long travelProductId) {
        List<Reservation> reservations = reservationRepository.findAllByTravelProductId(travelProductId);
        deleteReservationCascade(reservations);
        reservationRepository.deleteAllInBatch(reservations);
    }

    private void deleteReservationCascade(List<Reservation> reservations) {
        reviewDeleteService.deleteAllByReservations(reservations);
        liveSatisfactionDeleteService.deleteAllByReservations(reservations);
        liveFeedbackDeleteService.deleteAllByReservations(reservations);
    }
}
