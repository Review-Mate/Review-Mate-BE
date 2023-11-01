package com.somartreview.reviewmate.service.review;

import com.somartreview.reviewmate.domain.reservation.Reservation;
import com.somartreview.reviewmate.domain.review.*;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewGlobalDeleteService {

    private final ReviewRepository reviewRepository;
    private final ReviewTagRepository reviewTagRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final ReviewImageService reviewImageService;

    @Transactional
    public void deleteReviewByReviewId(Long reviewId) {
        Review review = validateExistId(reviewId);

        deleteReviewCascade(List.of(review));
        reviewRepository.deleteById(reviewId);
    }

    private Review validateExistId(Long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isEmpty()) {
            throw new DomainLogicException(ErrorCode.REVIEW_NOT_FOUND);
        }

        return review.get();
    }

    @Transactional
    public void deleteAllByReservations(List<Reservation> reservations) {
        List<Review> reviews = reviewRepository.findAllByReservation(reservations);

        deleteReviewCascade(reviews);
        reviewRepository.deleteAllInBatch(reviews);
    }

    private void deleteReviewCascade(List<Review> reviews) {
        deleteReviewTagsByReviews(reviews);
        deleteReviewImagesByReviews(reviews);
    }

    @Transactional
    public void deleteReviewTagsByReviews(List<Review> reviews) {
        reviewTagRepository.deleteAllByReviewsInQuery(reviews);
    }

    @Transactional
    public void deleteReviewImagesByReviews(List<Review> reviews) {
        List<ReviewImage> reviewImages = reviewImageRepository.findReviewImagesByReviewIdsInQuery(reviews);
        reviewImageService.removeReviewImageFiles(reviewImages);

        reviewImageRepository.deleteAllByReviewsInQuery(reviews);
    }
}
