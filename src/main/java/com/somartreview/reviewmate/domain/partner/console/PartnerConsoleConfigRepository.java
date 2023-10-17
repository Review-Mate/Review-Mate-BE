package com.somartreview.reviewmate.domain.partner.console;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerConsoleConfigRepository extends JpaRepository<PartnerConsoleConfig, Long> {

    @Query("select c from PartnerConsoleConfig c where c.partnerCompanyDomain = :partnerDomain")
    PartnerConsoleConfig findByPartnerDomain(String partnerDomain);

    @Query("select c.targetReviewingRate from PartnerConsoleConfig c where c.partnerCompanyDomain = :partnerDomain")
    Float findTargetReviewingRateByPartnerDomain(String partnerDomain);

    @Query("select c.achievementTimeSeriesUnit from PartnerConsoleConfig c where c.partnerCompanyDomain = :partnerDomain")
    ConsoleTimeSeriesUnit findAchievementTimeSeriesUnitByPartnerDomain(String partnerDomain);

    @Query("select c from PartnerConsoleConfig c where c.partnerCompanyDomain = :partnerDomain")
    void deleteByPartnerDomain(String partnerDomain);
}
