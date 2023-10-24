package com.somartreview.reviewmate.domain.partner.console;

import com.somartreview.reviewmate.dto.partner.console.PartnerConsoleConfigUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PartnerConsoleConfig {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Float targetReviewingRate = 100f;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ConsoleTimeSeriesUnit achievementTimeSeriesUnit = ConsoleTimeSeriesUnit.QUARTERLY;

    @Column(nullable = false)
    private String partnerCompanyDomain;


    public PartnerConsoleConfig(String partnerCompanyDomain) {
        this.partnerCompanyDomain = partnerCompanyDomain;
    }

    public void update(PartnerConsoleConfigUpdateRequest request) {
        this.targetReviewingRate = request.getTargetReviewingRate();
        this.achievementTimeSeriesUnit = request.getAchievementTimeSeriesUnit();
    }
}
