package com.somartreview.reviewmate.service.review;

import com.somartreview.reviewmate.domain.product.SingleTravelProduct;
import com.somartreview.reviewmate.domain.reservation.Reservation;
import com.somartreview.reviewmate.domain.review.*;
import com.somartreview.reviewmate.dto.review.*;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import com.somartreview.reviewmate.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.somartreview.reviewmate.exception.ErrorCode.*;
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
        validateExistReviewByReservationId(reservation.getId());
        reservation.getTravelProduct().addReview(reviewCreateRequest.getRating());

        Review review = reviewCreateRequest.toEntity(reservation);
        reviewRepository.save(review);

        if (reviewImageFiles != null) {
            List<ReviewImage> reviewImages = createReviewImages(reviewImageFiles, review);
            review.appendReviewImage(reviewImages);
        }

        // Impl Requesting review inference through API gateway
        // Impl Requesting review inference through kafka

        return review.getId();
    }

    private void validateExistReviewByReservationId(Long reservationId) {
        if (reviewRepository.existsByReservation_Id(reservationId))
            throw new DomainLogicException(REVIEW_ALREADY_EXISTS_ON_RESERVATION);
    }

    private List<ReviewImage> createReviewImages(List<MultipartFile> reviewImageFiles, Review review) {
        return reviewImageFiles.stream()
                .map(reviewImageFile -> ReviewImage.builder()
                        .url(uploadReviewImageOnS3(reviewImageFile))
                        .review(review)
                        .build())
                .toList();
    }

    private String uploadReviewImageOnS3(MultipartFile reviewImage) {
        //  Impl uploading review image to S3 and get the url
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

    public Page<WidgetReviewResponse> searchWidgetReviewResponsesWithPaging(String partnerDomain, String travelProductPartnerCustomId,
                                                                            ReviewProperty property, String keyword,
                                                                            ReviewOrderCriteria orderCriteria,
                                                                            Integer page, Integer size) {
        List<WidgetReviewResponse> widgetReviewResponses = new ArrayList<>();
        WidgetReviewSearchCond widgetReviewSearchCond = new WidgetReviewSearchCond(property, keyword, orderCriteria);
        Pageable pageable = PageRequest.of(page, size);

        Page<Review> foundReviews = reviewRepository.searchWidgetReviews(partnerDomain, travelProductPartnerCustomId, widgetReviewSearchCond, pageable);
        for (Review review : foundReviews) {
            List<ReviewTag> foundReviewTags = reviewTagService.findReviewTagsByReviewId(review.getId());
            widgetReviewResponses.add(new WidgetReviewResponse(review, foundReviewTags));
        }

        long totalCount = foundReviews.getTotalElements();

        return new PageImpl<>(widgetReviewResponses, pageable, totalCount);
    }

    @Transactional
    public void updateById(Long id, ReviewUpdateRequest reviewUpdateRequest, List<MultipartFile> reviewImageFiles) {
        Review review = findById(id);

        review.getReservation().getTravelProduct().removeReview(review.getRating());
        review.clearReviewTags();
        review.clearReviewImages();

        review.updateReview(reviewUpdateRequest);
        review.getReservation().getTravelProduct().addReview(reviewUpdateRequest.getRating());
        List<ReviewImage> reviewImages = createReviewImages(reviewImageFiles, review);
        review.appendReviewImage(reviewImages);

        // Impl Requesting review inference through API gateway
        // Impl Requesting review inference through kafka
    }

    @Transactional
    public void deleteById(Long id) {
        Review review = findById(id);

        review.getReservation().getTravelProduct().removeReview(review.getRating());
        review.clearReviewTags();
        review.clearReviewImages();

        reviewRepository.delete(review);
    }

    public ProductReviewStatisticsResponse getReviewStatisticsResponses(final SingleTravelProduct singleTravelProduct) {
        float averageRating = singleTravelProduct.getRating();
        long reviewCount = singleTravelProduct.getReviewCount();
        int fiveStarRatingCount = singleTravelProduct.getFiveStarRatingCount();
        int fourStarRatingCount = singleTravelProduct.getFourStarRatingCount();
        int threeStarRatingCount = singleTravelProduct.getThreeStarRatingCount();
        int twoStarRatingCount = singleTravelProduct.getTwoStarRatingCount();
        int oneStarRatingCount = singleTravelProduct.getOneStarRatingCount();

        return ProductReviewStatisticsResponse.builder()
                .averageRating(averageRating)
                .reviewCount(reviewCount)
                .fiveStarRatingCount(fiveStarRatingCount)
                .fourStarRatingCount(fourStarRatingCount)
                .threeStarRatingCount(threeStarRatingCount)
                .twoStarRatingCount(twoStarRatingCount)
                .oneStarRatingCount(oneStarRatingCount)
                .build();
    }

    public List<ProductReviewTagStatisticsResponse> getProductReviewTagStatisticsResponses(SingleTravelProduct singleTravelProduct) {
        Map<ReviewProperty, ProductReviewTagStatisticsResponse> reviewTagStatisticsMap = new EnumMap<>(ReviewProperty.class);
        List<ReviewTagStatisticsDto> reviewTagStatisticsDtos = reviewRepository.findReviewTagStatisticsByTravelProductId(singleTravelProduct.getId());

        for (ReviewTagStatisticsDto reviewTagStatisticDto : reviewTagStatisticsDtos) {
            if (reviewTagStatisticsMap.get(reviewTagStatisticDto.getProperty()) == null) {
                ProductReviewTagStatisticsResponse productReviewTagStatisticsResponse = new ProductReviewTagStatisticsResponse(reviewTagStatisticDto.getProperty());
                reviewTagStatisticsMap.put(reviewTagStatisticDto.getProperty(), productReviewTagStatisticsResponse);
            }

            if (reviewTagStatisticDto.getPolarity() == ReviewPolarity.POSITIVE) {
                reviewTagStatisticsMap.get(reviewTagStatisticDto.getProperty()).setPositiveCount(reviewTagStatisticDto.getCount());
            } else {
                reviewTagStatisticsMap.get(reviewTagStatisticDto.getProperty()).setNegativeCount(reviewTagStatisticDto.getCount());
            }
        }

        List<ProductReviewTagStatisticsResponse> reviewTagStatisticsResponses = new ArrayList<>(reviewTagStatisticsMap.values().stream().toList());
        reviewTagStatisticsResponses.sort(ReviewService::reviewTagCountDescComparator);

        return reviewTagStatisticsResponses;
    }

    private static int reviewTagCountDescComparator(ProductReviewTagStatisticsResponse o1, ProductReviewTagStatisticsResponse o2) {
        return -1 * Long.compare(o1.getPositiveCount() + o1.getNegativeCount(), o2.getPositiveCount() + o2.getNegativeCount());
    }
}
