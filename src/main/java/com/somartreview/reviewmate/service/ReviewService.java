package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.Customer.Customer;
import com.somartreview.reviewmate.domain.Review.*;
import com.somartreview.reviewmate.domain.TravelProduct.TravelProduct;
import com.somartreview.reviewmate.dto.request.review.ReviewCreateRequest;
import com.somartreview.reviewmate.dto.request.review.ReviewUpdateRequest;
import com.somartreview.reviewmate.dto.response.review.WidgetReviewResponse;
import com.somartreview.reviewmate.exception.DomainLogicException;
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
    private final CustomerService customerService;
    private final TravelProductService travelProductService;

    @Transactional
    public Long createReview(ReviewCreateRequest reviewCreateRequest, List<MultipartFile> reviewImageFiles) {
        final Customer customer = customerService.findCustomerById(reviewCreateRequest.getCustomerId());
        final TravelProduct travelProduct = travelProductService.findTravelProductById(reviewCreateRequest.getTravelProductId());

        Review review = reviewCreateRequest.toEntity(customer, travelProduct);
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

    public Review findReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(REVIEW_NOT_FOUND));
    }

    // TODO: Apply complicated condition by QueryDSL
    public List<WidgetReviewResponse> getWidgetReviewsByTravelProductId(Long travelProductId,
                                                                        Property property, String keyword,
                                                                        OrderCriteria orderCriteria,
                                                                        Integer page, Integer size) {
        List<WidgetReviewResponse> widgetReviewResponses = new ArrayList<>();

        List<Review> foundReviews = reviewRepository.findAllByTravelProduct_Id(travelProductId);
        for (Review review : foundReviews) {
            List<ReviewTag> foundReviewTags = reviewTagService.findReviewTagsByReviewId(review.getId());
            widgetReviewResponses.add(new WidgetReviewResponse(review, foundReviewTags));
        }

        return widgetReviewResponses;
    }

    @Transactional
    public void updateReviewById(Long id, ReviewUpdateRequest reviewUpdateRequest, List<MultipartFile> reviewImageFiles) {
        Review review = findReviewById(id);
        review.clearReviewTags();
        review.clearReviewImages();
        review.updateReview(reviewUpdateRequest);

        List<ReviewImage> reviewImages = createReviewImages(reviewImageFiles);
        review.appendReviewImage(reviewImages);

        // TODO: Request review tag through SQS
    }

    @Transactional
    public void deleteReviewById(Long id) {
        Review review = findReviewById(id);
        review.clearReviewTags();
        review.clearReviewImages();
        reviewRepository.delete(review);
    }
}
