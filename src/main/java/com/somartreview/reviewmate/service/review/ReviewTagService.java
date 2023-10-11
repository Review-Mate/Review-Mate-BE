package com.somartreview.reviewmate.service.review;

import com.somartreview.reviewmate.domain.review.ReviewTag;
import com.somartreview.reviewmate.domain.review.ReviewTagRepository;
import com.somartreview.reviewmate.dto.review.ReviewTagClassificationDto;
import com.somartreview.reviewmate.service.products.TravelProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

// Add positiveTags and negativeTags assignment in TravelProduct DTO after implementing reviewTag service
@Service
@RequiredArgsConstructor
public class ReviewTagService {

    private final ReviewTagRepository reviewTagRepository;
    private final TravelProductService travelProductService;

    public List<ReviewTag> findReviewTagsByReviewId(Long reviewId) {
        return reviewTagRepository.findAllByReview_Id(reviewId);
    }

    public List<ReviewTagClassificationDto> getDistinctReviewTagClassificationDtosByPartnerDomainAndTravelProductPartnerCustomId(String partnerDomain, String travelProductPartnerCustomId) {
        Long travelProductId = travelProductService.findByPartnerDomainAndPartnerCustomId(partnerDomain, travelProductPartnerCustomId).getId();

        return reviewTagRepository.getDistinctReviewTagClassificationsByTravelProductId(travelProductId);
    }
}
