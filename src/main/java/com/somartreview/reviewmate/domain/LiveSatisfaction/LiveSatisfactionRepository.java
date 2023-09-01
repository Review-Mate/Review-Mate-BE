package com.somartreview.reviewmate.domain.LiveSatisfaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveSatisfactionRepository extends JpaRepository<LiveSatisfaction, Long> {
}
