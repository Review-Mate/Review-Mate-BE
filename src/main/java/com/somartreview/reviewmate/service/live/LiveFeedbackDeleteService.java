package com.somartreview.reviewmate.service.live;

import com.somartreview.reviewmate.domain.live.feedback.LiveFeedbackRepository;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LiveFeedbackDeleteService {

    private final LiveFeedbackRepository liveFeedbackRepository;


    @Transactional
    public void deleteById(Long id) {
        validateExistId(id);

        liveFeedbackRepository.deleteById(id);
    }

    private void validateExistId(Long id) {
        if (!liveFeedbackRepository.existsById(id)) {
            throw new DomainLogicException(ErrorCode.LIVE_FEEDBACK_NOT_FOUND);
        }
    }

    @Transactional
    public void deleteAllByReservationIds(List<Long> ids) {
        liveFeedbackRepository.deleteAllByReservationIdsInQuery(ids);
    }
}
