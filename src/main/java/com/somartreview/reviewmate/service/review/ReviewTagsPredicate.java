package com.somartreview.reviewmate.service.review;

import com.somartreview.reviewmate.domain.review.ReviewProperty;
import com.somartreview.reviewmate.domain.review.tag.ReviewTag;

import java.util.List;
import java.util.function.Predicate;

public class ReviewTagsPredicate {

    private final List<Predicate<ReviewTag>> predicates;

    public ReviewTagsPredicate(ReviewProperty property, String keyword) {
        if (keyword == null) {
            this.predicates = List.of(
                    reviewTag -> reviewTag.getReviewProperty().equals(property)
            );
        } else {
            this.predicates = List.of(
                    reviewTag -> reviewTag.getReviewProperty().equals(property),
                    reviewTag -> reviewTag.getKeyword().equals(keyword)
            );
        }
    }

    public List<Predicate<ReviewTag>> getPredicates() {
        return predicates;
    }
}
