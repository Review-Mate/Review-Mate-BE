package com.somartreview.reviewmate.service.live;

import com.somartreview.reviewmate.domain.live.feedback.LiveFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LiveFeedbackDeleteService {

    private final LiveFeedbackRepository liveFeedbackRepository;

    public void deleteByReservationId(Long reservationId) {
        liveFeedbackRepository.deleteByReservation_Id(reservationId);
    }
}
