package com.somartreview.reviewmate.service.live;

import com.somartreview.reviewmate.domain.live.feedback.LiveFeedback;
import com.somartreview.reviewmate.domain.live.feedback.LiveFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LiveFeedbackDeleteService {

    private final LiveFeedbackRepository liveFeedbackRepository;

    @Transactional
    public void delete(Long id) {
        liveFeedbackRepository.deleteById(id);
    }

    @Transactional
    public void deleteByReservationId(Long reservationId) {
        Optional<LiveFeedback> foundLiveFeedback = liveFeedbackRepository.findByReservation_Id(reservationId);
        if (foundLiveFeedback.isEmpty()) {
            return;
        }

        liveFeedbackRepository.deleteByReservation_Id(foundLiveFeedback.get().getId());
    }
}
