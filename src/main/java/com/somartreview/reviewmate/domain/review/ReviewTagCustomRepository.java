package com.somartreview.reviewmate.domain.review;

import com.somartreview.reviewmate.dto.review.ReviewTagClassificationDto;

import java.util.List;

public interface ReviewTagCustomRepository {

    List<ReviewTagClassificationDto> getDistinctReviewTagClassificationsByTravelProductId(Long travelProductId);
}
