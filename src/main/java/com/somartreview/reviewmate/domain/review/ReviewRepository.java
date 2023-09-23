package com.somartreview.reviewmate.domain.review;

import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends ReviewJpaRepository, ReviewCustomRepository {
}
