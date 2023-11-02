package com.somartreview.reviewmate.service.review;

import com.somartreview.reviewmate.domain.review.Review;
import com.somartreview.reviewmate.domain.review.tag.ReviewTag;
import com.somartreview.reviewmate.domain.review.tag.ReviewTagInferenceClient;
import com.somartreview.reviewmate.domain.review.tag.ReviewTagRepository;
import com.somartreview.reviewmate.dto.review.tag.ReviewTagClassificationDto;
import com.somartreview.reviewmate.dto.review.tag.ReviewTagCreateRequest;
import com.somartreview.reviewmate.dto.review.tag.ReviewTagInferenceRequest;
import com.somartreview.reviewmate.dto.review.tag.ReviewTagInferenceResponse;
import com.somartreview.reviewmate.service.products.TravelProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReviewTagService {

    private final ReviewTagRepository reviewTagRepository;
    private final TravelProductService travelProductService;
    private final ReviewTagInferenceClient reviewTagInferenceClient;


    @Async
    public void createAll(Review review) {
        // Ask inference review tag
        ReviewTagInferenceRequest reviewTagInferenceRequest = new ReviewTagInferenceRequest(review.getContent());
        ReviewTagInferenceResponse reviewTagInferenceResponse = reviewTagInferenceClient.inferenceReview(reviewTagInferenceRequest);

        // Create review tags
        List<ReviewTagCreateRequest> reviewTagCreateRequests = reviewTagInferenceResponse.getBody().getResults().stream().map(ReviewTagCreateRequest::new).toList();
        for (ReviewTagCreateRequest reviewTagCreateRequest : reviewTagCreateRequests) {
            ReviewTag reviewTag = reviewTagCreateRequest.toEntity(review);
            reviewTag = reviewTagRepository.save(reviewTag);
            review.addReviewTag(reviewTag);
        }
    }

    public List<ReviewTag> findReviewTagsByReviewId(Long reviewId) {
        return reviewTagRepository.findAllByReview_Id(reviewId);
    }

    public List<ReviewTagClassificationDto> getDistinctReviewTagClassificationDtosByPartnerDomainAndTravelProductPartnerCustomId(String partnerDomain, String travelProductPartnerCustomId) {
        Long travelProductId = travelProductService.findByPartnerDomainAndPartnerCustomId(partnerDomain, travelProductPartnerCustomId).getId();

        return reviewTagRepository.getDistinctReviewTagClassificationsByTravelProductId(travelProductId);
    }
}
