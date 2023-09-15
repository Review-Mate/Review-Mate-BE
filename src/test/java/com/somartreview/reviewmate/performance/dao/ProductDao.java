package com.somartreview.reviewmate.performance.dao;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

@Repository
@Profile("performance")
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final Random random;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.random = new Random();
    }

    public void batchInsertSingleTravelProduct(long sellerId, long companyId, int productsSize, int reviewCount) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO travel_product (dtype, created_at, updated_at, name, partner_custom_id, review_count, rating, thumbnail_url, category, partner_company_id, partner_seller_id) " +
                        "VALUES ('SingleTravelProduct', now(), now(), ?, ?, ?, ?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, "product" + i);
                        ps.setString(2, "PRODUCT_" + i);
                        ps.setInt(3, reviewCount);
                        ps.setFloat(4, random.nextFloat(4) + 1);
                        ps.setString(5, "www.product-thumbnail" + i + ".com");
                        ps.setString(6, "ACCOMMODATION");
                        ps.setLong(7, companyId);
                        ps.setLong(8, sellerId);
                    }

                    @Override
                    public int getBatchSize() {
                        return productsSize;
                    }
                });
    }

    public void batchInsertCustomers(long companyId, int customersSize) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO customer (created_at, updated_at, kakao_id, name, partner_custom_id, phone_number, partner_company_id) " +
                        "VALUES (now(), now(), ?, ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, "kakaoId" + i);
                        ps.setString(2, "customer" + i);
                        ps.setString(3, "CUSTOMER_" + i);
                        ps.setString(4, "01012345678");
                        ps.setLong(5, companyId);
                    }

                    @Override
                    public int getBatchSize() {
                        return customersSize;
                    }
                });
    }
}
