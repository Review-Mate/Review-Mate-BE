package com.somartreview.reviewmate.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReviewTagRepository extends JpaRepository<ReviewTag, Long>, ReviewTagCustomRepository {

    List<ReviewTag> findAllByReview_Id(Long reviewId);

    @Transactional
    @Modifying
    @Query("delete from ReviewTag rt where rt.review.id in :ids")
    void deleteAllByReviewIdInQuery(List<Long> ids);
}
