package com.somartreview.reviewmate.service.live;

import com.somartreview.reviewmate.domain.live.satisfaction.LiveSatisfactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LiveSatisfactionService {

    private final LiveSatisfactionRepository liveSatisfactionRepository;

    public void deleteByReservationId(Long reservationId) {
        liveSatisfactionRepository.deleteByReservation_Id(reservationId);
    }
}
