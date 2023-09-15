package com.somartreview.reviewmate.performance.dao;

import com.somartreview.reviewmate.domain.review.ReviewPolarity;
import com.somartreview.reviewmate.domain.review.ReviewProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

@Repository
@Profile("performance")
public class ReviewDao {

    private final JdbcTemplate jdbcTemplate;
    private final Random random;

    public ReviewDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.random = new Random();
    }


    public void batchInsertReservations(long customerId, long travelProductId, int reservationsSize) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO reservation (created_at, updated_at, partner_custom_id, start_date_time, end_date_time, customer_id, travel_product_id) " +
                        "VALUES (now(), now(), ?, '2023-10-18T13:00:00', '2023-10-19T12:00:00', ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, "RESERVATION_" + i);
                        ps.setLong(2, customerId);
                        ps.setLong(3, travelProductId);
                    }

                    @Override
                    public int getBatchSize() {
                        return reservationsSize;
                    }
                });
    }

    public void batchInsertReviews(long reservationId, int reviewsSize) {
        ReviewPolarity[] polarities = ReviewPolarity.values();

        jdbcTemplate.batchUpdate(
                "INSERT INTO review (created_at, updated_at, polarity, content, rating, title, reservation_id) " +
                        "VALUES (now(), now(), ?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, polarities[i % polarities.length].name());
                        ps.setString(2, "content" + i);
                        ps.setInt(3, random.nextInt(5) + 1);
                        ps.setString(4, "title" + i);
                        ps.setLong(5, reservationId);
                    }

                    @Override
                    public int getBatchSize() {
                        return reviewsSize;
                    }
                });
    }

    public void batchInsertReviewTags(long reviewId, int reviewTagsSize) {
        ReviewPolarity[] polarities = ReviewPolarity.values();
        ReviewProperty[] properties = ReviewProperty.values();

        jdbcTemplate.batchUpdate(
                "INSERT INTO review_tag (created_at, updated_at, keyword, polarity, property, start_index, end_index, review_id) " +
                        "VALUES (now(), now(), ?, ?, ?, 1, 3, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, "tag" + i);
                        ps.setString(2, polarities[i % polarities.length].name());
                        ps.setString(2, properties[i % properties.length].name());
                        ps.setLong(3, reviewId);
                    }

                    @Override
                    public int getBatchSize() {
                        return reviewTagsSize;
                    }
                });
    }

    public void batchInsertReviewImage(long reviewId, int reviewImagesSize) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO review_image (created_at, updated_at, url, review_id) " +
                        "VALUES (now(), now(), ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, "www.review-image" + i + ".com");
                        ps.setLong(2, reviewId);
                    }

                    @Override
                    public int getBatchSize() {
                        return reviewImagesSize;
                    }
                });
    }
}
