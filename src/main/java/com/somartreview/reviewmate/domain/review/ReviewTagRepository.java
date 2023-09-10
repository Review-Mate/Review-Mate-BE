package com.somartreview.reviewmate.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewTagRepository extends JpaRepository<ReviewTag, Long> {

    List<ReviewTag> findAllByReview_Id(Long reviewId);
}
