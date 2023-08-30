package com.somartreview.reviewmate.domain.Review;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.Customer.Customer;
import com.somartreview.reviewmate.domain.TravelProduct.TravelProduct;
import com.somartreview.reviewmate.dto.request.review.ReviewUpdateRequest;
import com.somartreview.reviewmate.exception.DomainLogicException;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.somartreview.reviewmate.domain.Review.ReviewPolarity.*;
import static com.somartreview.reviewmate.exception.ErrorCode.*;

@Entity
@Getter
@NoArgsConstructor
public class Review extends BaseEntity {

    private static final int MAX_TITLE_LENGTH = 255;
    private static final int MAX_CONTENT_LENGTH = 255;
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 5;


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, name = "polarity")
    private ReviewPolarity polarityValue = NEUTRAL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_product_id", nullable = false)
    private TravelProduct travelProduct;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewTag> reviewTags = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImage> reviewImages = new ArrayList<>();

    @Builder
    public Review(Integer rating, String title, String content, Customer customer, TravelProduct travelProduct) {
        validateRating(rating);
        this.rating = rating;
        validateTitle(title);
        this.title = title;
        validateContent(content);
        this.content = content;
        this.customer = customer;
        travelProduct.addReview(this);
        this.travelProduct = travelProduct;
    }

    private void validateRating(final Integer rating) {
        if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new DomainLogicException(REVIEW_RATING_ERROR);
        }
    }

    private void validateTitle(final String title) {
        if (title.isBlank() || title.length() > MAX_TITLE_LENGTH) {
            throw new DomainLogicException(REVIEW_TITLE_ERROR);
        }
    }

    private void validateContent(final String content) {
        if (content.isBlank() || content.length() > MAX_CONTENT_LENGTH) {
            throw new DomainLogicException(REVIEW_CONTENT_ERROR);
        }
    }

    public void addReviewTag(ReviewTag reviewTag) {
        this.reviewTags.add(reviewTag);
    }

    public void clearReviewTags() {
        this.reviewTags.clear();
        this.polarityValue = NEUTRAL;
    }

    public void appendReviewImage(List<ReviewImage> reviewImages) {
        this.reviewImages.addAll(reviewImages);
    }

    public void clearReviewImages() {
        this.reviewImages.clear();
    }

    public void updateReview(ReviewUpdateRequest reviewUpdateRequest) {
        validateRating(reviewUpdateRequest.getRating());
        this.rating = reviewUpdateRequest.getRating();
        validateTitle(reviewUpdateRequest.getTitle());
        this.title = reviewUpdateRequest.getTitle();
        validateContent(reviewUpdateRequest.getContent());
        this.content = reviewUpdateRequest.getContent();
    }
}
