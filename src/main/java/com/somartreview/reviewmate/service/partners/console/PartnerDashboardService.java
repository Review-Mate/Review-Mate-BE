package com.somartreview.reviewmate.service.partners.console;

import com.somartreview.reviewmate.domain.partner.console.ConsoleTimeSeriesUnit;
import com.somartreview.reviewmate.domain.reservation.ReservationRepository;
import com.somartreview.reviewmate.domain.review.ReviewRepository;
import com.somartreview.reviewmate.dto.partner.console.ReviewingAchievementGaugeChartResponse;
import com.somartreview.reviewmate.service.partners.company.PartnerCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PartnerDashboardService {

    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;
    private final PartnerCompanyService partnerCompanyService;
    private final PartnerConsoleConfigService partnerConsoleConfigService;


    public Float getReviewingRate(String partnerDomain, ConsoleTimeSeriesUnit consoleTimeSeriesUnit) {
        partnerCompanyService.validateExistPartnerDomain(partnerDomain);

        LocalDateTime countingStartDateTime = getCountingStartDateTime(consoleTimeSeriesUnit);
        List<Long> reviewFks = reservationRepository.findAllReviewFKsByCreatedAtGreaterThanEqual(partnerDomain, countingStartDateTime);

        long todayReservationCount = reviewFks.size();
        if (todayReservationCount == 0) {
            return 0f;
        }

        long todayReviewCount = reviewFks.stream().filter(Objects::nonNull).count();
        return (float) todayReviewCount / todayReservationCount * 100;
    }

    private LocalDateTime getCountingStartDateTime(ConsoleTimeSeriesUnit consoleTimeSeriesUnit) {
        LocalDateTime now = LocalDateTime.now();
        return switch (consoleTimeSeriesUnit) {
            case DAILY -> LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);
            case WEEKLY -> now.with(DayOfWeek.MONDAY);
            case MONTHLY -> LocalDateTime.of(now.getYear(), now.getMonth(), 1, 0, 0, 0);
            case QUARTERLY -> LocalDateTime.of(now.getYear(), now.getMonth().firstMonthOfQuarter(), 1, 0, 0, 0);
            case HALF_YEARLY -> LocalDateTime.of(now.getYear(), now.getMonthValue() < 7 ? 1 : 7, 1, 0, 0, 0);
            case YEARLY -> LocalDateTime.of(now.getYear(), 1, 1, 0, 0, 0);
        };
    }

    public Long getTotalReviewCount(String partnerDomain) {
        partnerCompanyService.validateExistPartnerDomain(partnerDomain);

        return reviewRepository.countByPartnerDomain(partnerDomain);
    }

    public ReviewingAchievementGaugeChartResponse getReviewingAchievement(String partnerDomain) {
        partnerCompanyService.validateExistPartnerDomain(partnerDomain);

        ConsoleTimeSeriesUnit achievementTimeSeriesUnit = partnerConsoleConfigService.getAchievementTimeSeriesUnit(partnerDomain);
        float reviewingRate = getReviewingRate(partnerDomain, achievementTimeSeriesUnit);
        float targetReviewingRate = partnerConsoleConfigService.getTargetReviewingRate(partnerDomain);

        return ReviewingAchievementGaugeChartResponse.builder()
                .achievementPeriodUnit(achievementTimeSeriesUnit)
                .reviewingRate(reviewingRate)
                .targetReviewingRate(targetReviewingRate)
                .reviewingAchievementRate(reviewingRate / targetReviewingRate * 100)
                .build();

    }
}
