package com.somartreview.reviewmate.domain.Review;

import com.somartreview.reviewmate.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ReviewTag extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "review_tag_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Property category;

    private String keyword;

    @Column(nullable = false)
    private Double polarityValue;

    @Column(nullable = false)
    private Integer startIndex;

    @Column(nullable = false)
    private Integer endIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    public ReviewTag(Property category, String keyword, Double polarityValue, Integer startIndex, Integer endIndex, Review review) {
        this.category = category;
        this.keyword = keyword;
        this.polarityValue = polarityValue;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        review.addReviewTag(this);
        this.review = review;
    }
}
