package com.somartreview.reviewmate.domain.review;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.somartreview.reviewmate.dto.review.ReviewRatingCountsDto;
import com.somartreview.reviewmate.service.review.WidgetReviewSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.somartreview.reviewmate.domain.partner.company.QPartnerCompany.partnerCompany;
import static com.somartreview.reviewmate.domain.product.QTravelProduct.travelProduct;
import static com.somartreview.reviewmate.domain.reservation.QReservation.reservation;
import static com.somartreview.reviewmate.domain.review.QReview.review;
import static com.somartreview.reviewmate.domain.review.ReviewOrderCriteria.*;
import static com.somartreview.reviewmate.domain.review.tag.QReviewTag.reviewTag;

public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory queryFactory;

    public ReviewCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Review> searchWidgetReviews(String partnerDomain, String travelProductPartnerCustomId, WidgetReviewSearchCond searchCond, Pageable pageable) {
        List<Review> reviews = queryFactory
                .select(review)
                .from(review)
                .innerJoin(review.reservation, reservation)
                .innerJoin(reservation.travelProduct, travelProduct)
                .innerJoin(travelProduct.partnerCompany, partnerCompany)
                .where(
                        partnerCompany.partnerDomain.eq(partnerDomain),
                        travelProduct.partnerCustomId.eq(travelProductPartnerCustomId),
                        reviewTagEq(searchCond.getProperty(), searchCond.getKeyword())
                )
                .orderBy(orderCriteria(searchCond.getOrderCriteria()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory
                .select(review.count())
                .from(review)
                .where(review.in(reviews))
                .fetchOne();

        return new PageImpl<>(reviews, pageable, totalCount);
    }

    private BooleanExpression reviewTagEq(ReviewProperty property, String keyword) {
        if (property == null && keyword == null) return null;
        else if (keyword == null) return propertyEq(property);
        else return keywordEq(property, keyword);
    }

    private BooleanExpression propertyEq(ReviewProperty property) {
        return property == null ? null : (
                JPAExpressions
                        .selectOne()
                        .from(reviewTag)
                        .where(
                                reviewTag.review.eq(review)
                                        .and(reviewTag.reviewProperty.eq(property))
                        ).exists()
        );
    }

    private BooleanExpression keywordEq(ReviewProperty property, String keyword) {
        return keyword == null ? null : (
                JPAExpressions
                        .selectOne()
                        .from(reviewTag)
                        .where(
                                reviewTag.review.eq(review)
                                        .and(reviewTag.reviewProperty.eq(property))
                                        .and(reviewTag.keyword.eq(keyword))
                        ).exists()
        );
    }

    private OrderSpecifier<?> orderCriteria(ReviewOrderCriteria orderCriteria) {
        if (orderCriteria == null) return review.createdAt.desc();
        else if (orderCriteria.equals(LATEST)) return review.createdAt.desc();
        else if (orderCriteria.equals(RATING_ASC)) return review.rating.asc();
        else if (orderCriteria.equals(RATING_DESC)) return review.rating.desc();
        else if (orderCriteria.equals(POSITIVE_DESC)) return review.positiveTagsCount.desc();
        else if (orderCriteria.equals(NEGATIVE_DESC)) return review.negativeTagsCount.desc();
        else return null;
    }

    @Override
    public ReviewRatingCountsDto countReviewRatingByTravelProductId(Long travelProductId) {
        List<Tuple> results = queryFactory
                .select(review.rating, review.count())
                .from(review)
                .where(review.reservation.travelProduct.id.eq(travelProductId))
                .groupBy(review.rating)
                .having(review.rating.in(1, 2, 3, 4, 5))
                .orderBy(review.rating.asc())
                .fetch();

        ReviewRatingCountsDto reviewRatingCountsDto = new ReviewRatingCountsDto();
        for (Tuple tuple : results) {
            if (tuple.get(review.rating) == 1) reviewRatingCountsDto.setOneStarRatingCount(tuple.get(review.count()));
            else if (tuple.get(review.rating) == 2)
                reviewRatingCountsDto.setTwoStarRatingCount(tuple.get(review.count()));
            else if (tuple.get(review.rating) == 3)
                reviewRatingCountsDto.setThreeStarRatingCount(tuple.get(review.count()));
            else if (tuple.get(review.rating) == 4)
                reviewRatingCountsDto.setFourStarRatingCount(tuple.get(review.count()));
            else if (tuple.get(review.rating) == 5)
                reviewRatingCountsDto.setFiveStarRatingCount(tuple.get(review.count()));
        }

        return reviewRatingCountsDto;
    }
}
