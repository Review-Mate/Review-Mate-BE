package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.Review.ReviewTag;
import com.somartreview.reviewmate.domain.Review.ReviewTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewTagService {

    private final ReviewTagRepository reviewTagRepository;

    public List<ReviewTag> findReviewTagsByReviewId(Long reviewId) {
        return reviewTagRepository.findAllByReview_Id(reviewId);
    }
}
