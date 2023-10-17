package com.somartreview.reviewmate.domain.partner.console;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerConsoleConfigRepository extends JpaRepository<PartnerConsoleConfig, Long> {

    @Query("select c from PartnerConsoleConfig c where c = :partnerDomain")
    PartnerConsoleConfig findByPartnerDomain(String partnerDomain);

    @Query("select c.targetReviewingRate from PartnerConsoleConfig c where c.partnerCompanyDomain = :partnerDomain")
    Float findTargetReviewingRateByPartnerDomain(String partnerDomain);

    @Query("select c.achievementPeriodUnit from PartnerConsoleConfig c where c.partnerCompanyDomain = :partnerDomain")
    AchievementPeriodUnit findAchievementPeriodUnitByPartnerDomain(String partnerDomain);
}
