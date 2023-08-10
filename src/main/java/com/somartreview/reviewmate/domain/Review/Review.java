package com.somartreview.reviewmate.domain.Review;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.domain.Customer.Customer;
import com.somartreview.reviewmate.domain.TravelProduct.TravelProduct;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Review extends BaseEntity {

    private static final int MAX_TITLE_LENGTH = 255;
    private static final int MAX_CONTENT_LENGTH = 255;


    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Double polarityValue = 0.0;

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

    public Review(Integer rating, String title, String content, Customer customer, TravelProduct travelProduct) {
        this.rating = rating;
        validateTitle(title);
        this.title = title;
        validateContent(content);
        this.content = content;
        this.customer = customer;
        travelProduct.addReview(this);
        this.travelProduct = travelProduct;
    }

    private void validateTitle(final String title) {
        if (title.isBlank() || title.length() > MAX_TITLE_LENGTH) {
            throw new DomainLogicException(ErrorCode.REVIEW_TITLE_ERROR);
        }
    }

    private void validateContent(final String content) {
        if (content.isBlank() || content.length() > MAX_CONTENT_LENGTH) {
            throw new DomainLogicException(ErrorCode.REVIEW_CONTENT_ERROR);
        }
    }

    public void addReviewTag(ReviewTag reviewTag) {
        int reviewTagCount = this.reviewTags.size();
        polarityValue = (polarityValue * reviewTagCount + reviewTag.getPolarityValue()) / (reviewTagCount + 1);

        this.reviewTags.add(reviewTag);
    }

    public void addReviewImage(ReviewImage reviewImage) {
        this.reviewImages.add(reviewImage);
    }
}
