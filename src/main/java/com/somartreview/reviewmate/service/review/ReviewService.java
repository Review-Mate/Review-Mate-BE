package com.somartreview.reviewmate.service.review;

import com.somartreview.reviewmate.domain.product.SingleTravelProduct;
import com.somartreview.reviewmate.domain.reservation.Reservation;
import com.somartreview.reviewmate.domain.review.*;
import com.somartreview.reviewmate.domain.review.tag.ReviewTag;
import com.somartreview.reviewmate.dto.review.*;
import com.somartreview.reviewmate.dto.review.tag.ReviewTagStatisticsDto;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.service.ReservationService;
import com.somartreview.reviewmate.service.products.SingleTravelProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

import static com.somartreview.reviewmate.exception.ErrorCode.*;
import static com.somartreview.reviewmate.exception.ErrorCode.REVIEW_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageService reviewImageService;
    private final ReviewTagService reviewTagService;
    private final ReviewGlobalDeleteService reviewGlobalDeleteService;
    private final ReservationService reservationService;
    private final SingleTravelProductService singleTravelProductService;


    @Transactional
    public Long create(String partnerDomain, String reservationPartnerCustomId, ReviewCreateRequest reviewCreateRequest, List<MultipartFile> reviewImageFiles) {
        // Get reservation
        final Reservation reservation = reservationService.findByPartnerDomainAndPartnerCustomId(partnerDomain, reservationPartnerCustomId);
        validateAlreadyReviewedByReservationId(reservation.getId());

        // Create review
        Review review = reviewCreateRequest.toEntity(reservation);
        reviewRepository.save(review);
        reservation.getTravelProduct().addReviewInfo(review.getRating());

        // Create review images
        if (reviewImageFiles != null) {
            reviewImageService.createAll(reviewImageFiles, review);
        }

        // Create review tag
        reviewTagService.createAll(review);
        // Impl Requesting review inference through kafka

        return review.getId();
    }

    private void validateAlreadyReviewedByReservationId(Long reservationId) {
        if (reviewRepository.existsByReservationId(reservationId))
            throw new DomainLogicException(REVIEW_ALREADY_EXISTS_ON_RESERVATION);
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(REVIEW_NOT_FOUND));
    }

    public WidgetReviewResponse getWidgetReviewResponseById(Long id) {
        final Review review = findById(id);

        return new WidgetReviewResponse(review);
    }

    public Page<WidgetReviewResponse> searchWidgetReviewResponsesWithPaging(String partnerDomain, String travelProductPartnerCustomId,
                                                                            ReviewProperty property, String keyword,
                                                                            ReviewOrderCriteria orderCriteria,
                                                                            Integer page, Integer size) {
        WidgetReviewSearchCond widgetReviewSearchCond = new WidgetReviewSearchCond(property, keyword, orderCriteria);
        Pageable pageable = PageRequest.of(page, size);

        Page<Review> reviews = reviewRepository.searchWidgetReviews(partnerDomain, travelProductPartnerCustomId, widgetReviewSearchCond, pageable);
        List<WidgetReviewResponse> widgetReviewResponses = reviews.stream().map(review -> new WidgetReviewResponse(review, property, keyword)).toList();

        return new PageImpl<>(widgetReviewResponses, pageable, reviews.getTotalElements());
    }

    public ProductReviewStatisticsResponse getReviewStatisticsResponses(String partnerDomain, String singleTravelProductPartnerCustomId) {
        final SingleTravelProduct singleTravelProduct = singleTravelProductService.findByPartnerDomainAndPartnerCustomId(partnerDomain, singleTravelProductPartnerCustomId);
        ReviewRatingCountsDto reviewRatingCountsDto = reviewRepository.countReviewRatingByTravelProductId(singleTravelProduct.getId());

        return ProductReviewStatisticsResponse.builder()
                .averageRating(singleTravelProduct.getRating())
                .reviewCount(singleTravelProduct.getReviewCount())
                .oneStarRatingCount(reviewRatingCountsDto.getOneStarRatingCount())
                .twoStarRatingCount(reviewRatingCountsDto.getTwoStarRatingCount())
                .threeStarRatingCount(reviewRatingCountsDto.getThreeStarRatingCount())
                .fourStarRatingCount(reviewRatingCountsDto.getFourStarRatingCount())
                .fiveStarRatingCount(reviewRatingCountsDto.getFiveStarRatingCount())
                .build();
    }

    public List<ProductReviewTagStatisticsResponse> getProductReviewTagStatisticsResponses(String partnerDomain, String singleTravelProductPartnerCustomId) {
        long singleTravelProductId = singleTravelProductService.findByPartnerDomainAndPartnerCustomId(partnerDomain, singleTravelProductPartnerCustomId).getId();

        Map<ReviewProperty, ProductReviewTagStatisticsResponse> reviewTagStatisticsMap = new EnumMap<>(ReviewProperty.class);
        List<ReviewTagStatisticsDto> reviewTagStatisticsDtos = reviewRepository.findReviewTagStatisticsByTravelProductId(singleTravelProductId);

        for (ReviewTagStatisticsDto reviewTagStatisticDto : reviewTagStatisticsDtos) {
            if (!reviewTagStatisticsMap.containsKey(reviewTagStatisticDto.getProperty())) {
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

    @Transactional
    public void update(Long id, ReviewUpdateRequest request, List<MultipartFile> reviewImageFiles) {
        Review review = findById(id);

        review.getReservation().getTravelProduct().subtractReviewInfo(review.getRating());
        List<Review> reviews = List.of(review);
        review.clearReviewImages();
        reviewGlobalDeleteService.deleteReviewImagesByReviews(reviews);
        review.clearReviewTags();
        reviewGlobalDeleteService.deleteReviewTagsByReviews(reviews);

        review.updateReview(request);
        review.getReservation().getTravelProduct().addReviewInfo(request.getRating());
        reviewImageService.createAll(reviewImageFiles, review);

        // Impl Requesting review inference through API gateway
        // Impl Requesting review inference through kafka
    }
}
