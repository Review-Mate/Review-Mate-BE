package com.somartreview.reviewmate.domain.live.satisfaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LiveSatisfactionRepository extends JpaRepository<LiveSatisfaction, Long> {
    void deleteByReservation_Id(Long reservationId);

    Optional<LiveSatisfaction> findByReservation_Id(Long reservationId);
}
