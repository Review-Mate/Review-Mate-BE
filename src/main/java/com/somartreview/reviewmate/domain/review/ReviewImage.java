package com.somartreview.reviewmate.domain.review;

import com.somartreview.reviewmate.domain.BaseEntity;
import com.somartreview.reviewmate.exception.DomainLogicException;
import com.somartreview.reviewmate.exception.ErrorCode;
import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ReviewImage extends BaseEntity {

    private static final int MAX_URL_LENGTH = 1024;


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_image_id")
    private Long id;

    @Column(nullable = false, length = 1024)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Builder
    public ReviewImage(String url, Review review) {
        validateUrl(url);
        this.url = url;
        this.review = review;
    }

    private void validateUrl(final String url) {
        if (url.isBlank() || url.length() > MAX_URL_LENGTH) {
            throw new DomainLogicException(ErrorCode.REVIEW_IMAGE_URL_ERROR);
        }
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
