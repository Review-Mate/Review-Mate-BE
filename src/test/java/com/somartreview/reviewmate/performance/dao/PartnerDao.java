package com.somartreview.reviewmate.performance.dao;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
@Profile("performance")
public class PartnerDao {

    private final JdbcTemplate jdbcTemplate;

    public PartnerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsertPartnerCompanies(int companiesSize) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO partner_company (created_at, updated_at, is_withdrawn, name, domain) VALUES (now(), now(), 0, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, "company" + i);
                        ps.setString(2, "company" + i + ".com");
                    }

                    @Override
                    public int getBatchSize() {
                        return companiesSize;
                    }
                });
    }

    public void batchInsertPartnerManagers(long companyId, int managersSize) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO partner_manager (created_at, updated_at, name, email, password, role, partner_company_id) VALUES (now(), now(), ?, ?, ?, 'ADMIN', ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, "manager" + i);
                        ps.setString(2, "manager" + i + "@company.com");
                        ps.setString(3, "password" + i);
                        ps.setLong(4, companyId);
                    }

                    @Override
                    public int getBatchSize() {
                        return managersSize;
                    }
                });
    }

    public void batchInsertPartnerSellers(long companyId, int sellersSize) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO partner_seller (created_at, updated_at, kakao_id, name, phone_number, partner_company_id) VALUES (now(), now(), ?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, "kakaoId" + i);
                        ps.setString(2, "seller" + i);
                        ps.setString(3, "01012345678");
                        ps.setLong(4, companyId);
                    }

                    @Override
                    public int getBatchSize() {
                        return sellersSize;
                    }
                });
    }
}
