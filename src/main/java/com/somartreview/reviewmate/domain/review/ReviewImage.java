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

    private static final int MAX_URL_LENGTH = 255;


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_image_id")
    private Long id;

    @Column(nullable = false, length = 255)
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Builder
    public ReviewImage(String fileName, Review review) {
        validateFileName(fileName);
        this.fileName = fileName;
        this.review = review;
    }

    private void validateFileName(final String fileName) {
        if (fileName.isBlank() || fileName.length() > MAX_URL_LENGTH) {
            throw new DomainLogicException(ErrorCode.REVIEW_IMAGE_URL_ERROR);
        }
    }
}
