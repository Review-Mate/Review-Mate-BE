package com.somartreview.reviewmate.domain.Review;

import lombok.Getter;

@Getter
public enum OrderCriteria {
    LATEST("LATEST"),
    RATING_ASC("RATING_ASC"),
    RATING_DESC("RATING_DESC"),
    POSITIVE("POSITIVE"),
    NEGATIVE("NEGATIVE");

    private final String value;

    OrderCriteria(String value) {
        this.value = value;
    }
}
