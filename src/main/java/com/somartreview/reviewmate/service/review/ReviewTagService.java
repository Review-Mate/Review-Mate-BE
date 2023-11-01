package com.somartreview.reviewmate.service.review;

import com.somartreview.reviewmate.domain.review.Review;
import com.somartreview.reviewmate.domain.review.tag.ReviewTag;
import com.somartreview.reviewmate.domain.review.tag.ReviewTagRepository;
import com.somartreview.reviewmate.dto.review.tag.ReviewTagClassificationDto;
import com.somartreview.reviewmate.dto.review.tag.ReviewTagCreateRequest;
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

    public List<ReviewTag> createAll(List<ReviewTagCreateRequest> reviewTagCreateRequests, Review review) {
        List<ReviewTag> reviewTags = new ArrayList<>();

        for (ReviewTagCreateRequest reviewTagCreateRequest : reviewTagCreateRequests) {
            ReviewTag reviewTag = reviewTagCreateRequest.toEntity(review);
            reviewTag = reviewTagRepository.save(reviewTag);
            reviewTags.add(reviewTag);
        }

        return reviewTags;
    }

    public List<ReviewTag> findReviewTagsByReviewId(Long reviewId) {
        return reviewTagRepository.findAllByReview_Id(reviewId);
    }

    public List<ReviewTagClassificationDto> getDistinctReviewTagClassificationDtosByPartnerDomainAndTravelProductPartnerCustomId(String partnerDomain, String travelProductPartnerCustomId) {
        Long travelProductId = travelProductService.findByPartnerDomainAndPartnerCustomId(partnerDomain, travelProductPartnerCustomId).getId();

        return reviewTagRepository.getDistinctReviewTagClassificationsByTravelProductId(travelProductId);
    }
}
