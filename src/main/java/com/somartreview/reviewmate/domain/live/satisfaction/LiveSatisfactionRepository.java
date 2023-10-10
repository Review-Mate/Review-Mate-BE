package com.somartreview.reviewmate.domain.live.satisfaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LiveSatisfactionRepository extends JpaRepository<LiveSatisfaction, Long> {

    @Transactional
    @Modifying
    @Query("delete from LiveSatisfaction ls where ls.id = :id")
    void deleteById(Long id);
    @Transactional
    @Modifying
    @Query("delete from LiveSatisfaction ls where ls.id in :ids")
    void deleteAllByIdsInQuery(List<Long> ids);
}
