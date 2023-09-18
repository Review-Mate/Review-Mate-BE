package com.somartreview.reviewmate.performance.dao;

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
public class LiveDao {

    private final JdbcTemplate jdbcTemplate;
    private final Random random;

    public LiveDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.random = new Random();
    }

    public void batchInsertLiveFeedbacks(long[] reservationIds, int liveFeedbacksSize) {
        boolean[][] statusSets = {
                {false, false, false},
                {true, true, false},
                {true, false, true}
        };

        jdbcTemplate.batchUpdate(
                "INSERT INTO live_feedback (created_at, updated_at, customer_media_url, customer_message, is_handled, is_reported, is_solved, seller_message, reservation_id) " +
                        "VALUES (now(), now(), ?, ?, ?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, "www.customer-media-url" + i + ".com");
                        ps.setString(2, "customer-message" + i);
                        ps.setBoolean(3, statusSets[i % statusSets.length][0]);
                        ps.setBoolean(4, statusSets[i % statusSets.length][1]);
                        ps.setBoolean(5, statusSets[i % statusSets.length][2]);
                        ps.setString(6, "seller-message" + i);
                        ps.setLong(7, reservationIds[i % reservationIds.length]);
                    }

                    @Override
                    public int getBatchSize() {
                        return liveFeedbacksSize;
                    }
                });
    }

    public void batchInsertLiveSatisfactions(long[] reservationIds, int liveSatisfactionsSize) {
        ReviewProperty[] properties = ReviewProperty.values();

        jdbcTemplate.batchUpdate(
                "INSERT INTO live_satisfaction (created_at, updated_at, dissatisfied_review_property, rating, satisfied_review_property, reservation_id) " +
                        "VALUES (now(), now(), ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, properties[i % properties.length].name());
                        ps.setLong(2, random.nextInt(5) + 1);
                        ps.setString(3, properties[i % properties.length].name());
                        ps.setLong(4, reservationIds[i % reservationIds.length]);
                    }

                    @Override
                    public int getBatchSize() {
                        return liveSatisfactionsSize;
                    }
                });
    }
}
