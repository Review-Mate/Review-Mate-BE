package com.somartreview.reviewmate.domain.live.satisfaction;

import com.somartreview.reviewmate.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LiveSatisfactionRepository extends JpaRepository<LiveSatisfaction, Long> {

    @Transactional
    @Modifying
    @Query("delete from LiveSatisfaction ls where ls.id = :id")
    void deleteById(Long id);


    @Transactional
    @Modifying
    @Query("delete from LiveSatisfaction ls where ls.reservation in :reservations")
    void deleteAllByReservationsInQuery(List<Reservation> reservations);
}
