package com.somartreview.reviewmate.domain.review.tag;

import com.somartreview.reviewmate.dto.review.tag.ReviewTagClassificationDto;

import java.util.List;

public interface ReviewTagCustomRepository {

    List<ReviewTagClassificationDto> getDistinctReviewTagClassificationsByTravelProductId(Long travelProductId);
}
