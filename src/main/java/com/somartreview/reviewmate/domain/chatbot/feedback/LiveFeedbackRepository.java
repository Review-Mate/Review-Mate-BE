package com.somartreview.reviewmate.domain.chatbot.feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveFeedbackRepository extends JpaRepository<LiveFeedback, Long> {
}
