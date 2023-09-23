package com.somartreview.reviewmate.domain.review;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.somartreview.reviewmate.dto.review.WidgetReviewResponse;
import com.somartreview.reviewmate.service.review.WidgetReviewSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.somartreview.reviewmate.domain.review.QReview.review;
import static com.somartreview.reviewmate.domain.review.ReviewOrderCriteria.*;

public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory queryFactory;

    public ReviewCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<Review> searchWidgetReviews(String partnerDomain, String travelProductPartnerCustomId, WidgetReviewSearchCond searchCond, Pageable pageable) {
        List<Review> content = queryFactory
                .select(review)
                .from(review)
                .where(
                        reviewTagEq(searchCond.getProperty(), searchCond.getKeyword())
                )
                .orderBy(orderCriteria(searchCond.getOrderCriteria()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory
                .select(review.count())
                .from(review)
                .where(
                        reviewTagEq(searchCond.getProperty(), searchCond.getKeyword())
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, totalCount);
    }

    private BooleanExpression reviewTagEq(ReviewProperty property, String keyword) {
        if (property == null && keyword == null) return null;
        else if (keyword == null) return propertyEq(property);
        else return keywordEq(property, keyword);
    }

    private BooleanExpression propertyEq(ReviewProperty property) {
        return property == null ? null : review.reviewTags.any().reviewProperty.eq(property);
    }

    private BooleanExpression keywordEq(ReviewProperty property, String keyword) {
        return keyword == null ? null : (
                review.reviewTags.any().reviewProperty.eq(property)
                        .and(review.reviewTags.any().keyword.eq(keyword))
        );
    }

    private OrderSpecifier<?> orderCriteria(ReviewOrderCriteria orderCriteria) {
        if (orderCriteria == null) return null;
        else if (orderCriteria.equals(LATEST)) return review.createdAt.desc();
        else if (orderCriteria.equals(RATING_ASC)) return review.rating.desc();
        else if (orderCriteria.equals(RATING_DESC)) return review.rating.asc();
        else if (orderCriteria.equals(POSITIVE)) return review.reviewTags.any().polarity.eq(ReviewPolarity.POSITIVE).count().desc();
        else if (orderCriteria.equals(NEGATIVE)) return review.reviewTags.any().polarity.eq(ReviewPolarity.NEGATIVE).count().desc();
        else return null;
    }
}
