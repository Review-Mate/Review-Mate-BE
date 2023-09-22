package com.somartreview.reviewmate.service.review;

import com.somartreview.reviewmate.domain.review.Review;
import com.somartreview.reviewmate.domain.review.ReviewRepository;
import com.somartreview.reviewmate.exception.DomainLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.somartreview.reviewmate.exception.ErrorCode.REVIEW_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ReviewDeleteService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public void delete(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new DomainLogicException(REVIEW_NOT_FOUND));

        review.getReservation().getTravelProduct().removeReviewData(review.getRating());
        review.clearReviewTags();
        review.clearReviewImages();

        reviewRepository.delete(review);
    }

    @Transactional
    public void deleteByReservationId(Long reservationId) {
        Optional<Review> foundReview = reviewRepository.findByReservation_Id(reservationId);
        if (foundReview.isEmpty()) {
            throw new DomainLogicException(REVIEW_NOT_FOUND);
        }

        delete(foundReview.get().getId());
    }
}
