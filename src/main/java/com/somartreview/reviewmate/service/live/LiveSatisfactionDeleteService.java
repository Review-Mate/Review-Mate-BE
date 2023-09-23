package com.somartreview.reviewmate.service.live;

import com.somartreview.reviewmate.domain.live.satisfaction.LiveSatisfaction;
import com.somartreview.reviewmate.domain.live.satisfaction.LiveSatisfactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LiveSatisfactionDeleteService {

    private final LiveSatisfactionRepository liveSatisfactionRepository;

    @Transactional
    public void delete(Long id) {
        liveSatisfactionRepository.deleteById(id);
    }

    @Transactional
    public void deleteByReservationId(Long reservationId) {
        Optional<LiveSatisfaction> foundLiveSatisfaction = liveSatisfactionRepository.findByReservation_Id(reservationId);
        if (foundLiveSatisfaction.isEmpty()) {
            return;
        }

        delete(foundLiveSatisfaction.get().getId());
    }
}
