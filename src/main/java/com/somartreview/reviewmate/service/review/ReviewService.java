package com.somartreview.reviewmate.service.review;

import com.somartreview.reviewmate.domain.Reservation.Reservation;
import com.somartreview.reviewmate.domain.Review.*;
import com.somartreview.reviewmate.dto.request.review.ReviewCreateRequest;
import com.somartreview.reviewmate.dto.request.review.ReviewUpdateRequest;
import com.somartreview.reviewmate.dto.response.review.WidgetReviewResponse;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.somartreview.reviewmate.exception.ErrorCode.REVIEW_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewTagService reviewTagService;
    private final ReservationService reservationService;

    @Transactional
    public Long create(String partnerDomain, String travelProductPartnerCustomId, ReviewCreateRequest reviewCreateRequest, List<MultipartFile> reviewImageFiles) {
        final Reservation reservation = reservationService.findByPartnerDomainAndPartnerCustomId(partnerDomain, travelProductPartnerCustomId);
        reservation.getTravelProduct().addReview(reviewCreateRequest.getRating());

        Review review = reviewCreateRequest.toEntity(reservation);
        reviewRepository.save(review);

        List<ReviewImage> reviewImages = createReviewImages(reviewImageFiles);
        review.appendReviewImage(reviewImages);

        // TODO: Request review tag through SQS

        return review.getId();
    }

    private List<ReviewImage> createReviewImages(List<MultipartFile> reviewImageFiles) {
        return reviewImageFiles.stream()
                .map(reviewImageFile -> ReviewImage.builder()
                        .url(uploadReviewImageOnS3(reviewImageFile))
                        .build())
                .toList();
    }

    private String uploadReviewImageOnS3(MultipartFile reviewImage) {
        //  TODO: Upload review image to S3 and get the url
        return "https://www.testThumbnailUrl.com";
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(REVIEW_NOT_FOUND));
    }

    public WidgetReviewResponse getWidgetReviewResponseById(Long id) {
        final Review review = findById(id);
        final List<ReviewTag> foundReviewTags = reviewTagService.findReviewTagsByReviewId(review.getId());

        return new WidgetReviewResponse(review, foundReviewTags);
    }

    // TODO: Apply complicated condition by QueryDSL
    public List<WidgetReviewResponse> getWidgetReviewResponsesByPartnerDomainAndTravelProductIdWithCondition(String partnerDomain, String travelProductPartnerCustomId,
                                                                                                             ReviewProperty reviewProperty, String keyword,
                                                                                                             ReviewOrderCriteria reviewOrderCriteria,
                                                                                                             Integer page, Integer size) {
        List<WidgetReviewResponse> widgetReviewResponses = new ArrayList<>();
        List<Review> foundReviews = reviewRepository.findAllByReservation_TravelProduct_PartnerCompany_PartnerDomainAndReservation_TravelProduct_PartnerCustomId(partnerDomain, travelProductPartnerCustomId);
        for (Review review : foundReviews) {
            List<ReviewTag> foundReviewTags = reviewTagService.findReviewTagsByReviewId(review.getId());
            widgetReviewResponses.add(new WidgetReviewResponse(review, foundReviewTags));
        }

        return widgetReviewResponses;
    }

    @Transactional
    public void updateById(Long id, ReviewUpdateRequest reviewUpdateRequest, List<MultipartFile> reviewImageFiles) {
        Review review = findById(id);

        review.getReservation().getTravelProduct().removeReview(review.getRating());
        review.clearReviewTags();
        review.clearReviewImages();

        review.updateReview(reviewUpdateRequest);
        review.getReservation().getTravelProduct().addReview(reviewUpdateRequest.getRating());
        List<ReviewImage> reviewImages = createReviewImages(reviewImageFiles);
        review.appendReviewImage(reviewImages);

        // TODO: Request review tag through SQS
    }

    @Transactional
    public void deleteById(Long id) {
        Review review = findById(id);

        review.getReservation().getTravelProduct().removeReview(review.getRating());
        review.clearReviewTags();
        review.clearReviewImages();

        reviewRepository.delete(review);
    }
}
