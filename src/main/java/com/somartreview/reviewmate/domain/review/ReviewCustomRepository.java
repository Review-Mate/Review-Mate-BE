package com.somartreview.reviewmate.domain.review;

import com.somartreview.reviewmate.dto.review.ReviewRatingCountsDto;
import com.somartreview.reviewmate.dto.review.WidgetReviewResponse;
import com.somartreview.reviewmate.service.review.WidgetReviewSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewCustomRepository {

    Page<WidgetReviewResponse> searchWidgetReviews(String partnerDomain, String travelProductPartnerCustomId, WidgetReviewSearchCond searchCond, Pageable pageable);

    ReviewRatingCountsDto countReviewRatingByTravelProductId(Long travelProductId);
}
