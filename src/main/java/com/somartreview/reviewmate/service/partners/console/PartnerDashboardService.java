package com.somartreview.reviewmate.service.partners.console;

import com.somartreview.reviewmate.domain.partner.console.ConsoleTimeSeriesUnit;
import com.somartreview.reviewmate.domain.reservation.ReservationRepository;
import com.somartreview.reviewmate.domain.review.ReviewRepository;
import com.somartreview.reviewmate.dto.partner.console.ReviewingAchievementBarChartResponse;
import com.somartreview.reviewmate.dto.partner.console.ReviewingAchievementGaugeChartResponse;
import com.somartreview.reviewmate.service.partners.company.PartnerCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PartnerDashboardService {

    private static final int REVIEWING_ACHIEVEMENT_BAR_CHART_HORIZONTAL_AXIS_LENGTH = 8;

    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;
    private final PartnerCompanyService partnerCompanyService;
    private final PartnerConsoleConfigService partnerConsoleConfigService;


    public Float getReviewingRate(String partnerDomain, LocalDateTime dateTime, ConsoleTimeSeriesUnit timeSeriesUnit) {
        partnerCompanyService.validateExistPartnerDomain(partnerDomain);

        LocalDateTime startDateTime = getStartDateTimeOfTimeSeriesUnit(dateTime, timeSeriesUnit);
        List<Long> reviewFks = reservationRepository.findAllReviewFKsByCreatedAtGreaterThanEqual(partnerDomain, startDateTime);

        long todayReservationCount = reviewFks.size();
        if (todayReservationCount == 0) {
            return 0f;
        }

        long todayReviewCount = reviewFks.stream().filter(Objects::nonNull).count();
        return (float) todayReviewCount / todayReservationCount * 100;
    }

    private LocalDateTime getStartDateTimeOfTimeSeriesUnit(LocalDateTime dateTime, ConsoleTimeSeriesUnit timeSeriesUnit) {
        return switch (timeSeriesUnit) {
            case DAILY -> LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), 0, 0, 0);
            case WEEKLY -> dateTime.with(DayOfWeek.MONDAY);
            case MONTHLY -> LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), 1, 0, 0, 0);
            case QUARTERLY ->
                    LocalDateTime.of(dateTime.getYear(), dateTime.getMonth().firstMonthOfQuarter(), 1, 0, 0, 0);
            case HALF_YEARLY -> LocalDateTime.of(dateTime.getYear(), dateTime.getMonthValue() < 7 ? 1 : 7, 1, 0, 0, 0);
            case YEARLY -> LocalDateTime.of(dateTime.getYear(), 1, 1, 0, 0, 0);
        };
    }

    public Long getTotalReviewCount(String partnerDomain) {
        partnerCompanyService.validateExistPartnerDomain(partnerDomain);

        return reviewRepository.countByPartnerDomain(partnerDomain);
    }

    public ReviewingAchievementGaugeChartResponse getReviewingAchievementGaugeChart(String partnerDomain) {
        partnerCompanyService.validateExistPartnerDomain(partnerDomain);

        LocalDateTime now = LocalDateTime.now();
        ConsoleTimeSeriesUnit achievementTimeSeriesUnit = partnerConsoleConfigService.getAchievementTimeSeriesUnit(partnerDomain);
        float reviewingRate = getReviewingRate(partnerDomain, now, achievementTimeSeriesUnit);
        float targetReviewingRate = partnerConsoleConfigService.getTargetReviewingRate(partnerDomain);

        return ReviewingAchievementGaugeChartResponse.builder()
                .achievementPeriodUnit(achievementTimeSeriesUnit)
                .reviewingRate(reviewingRate)
                .targetReviewingRate(targetReviewingRate)
                .reviewingAchievementRate(reviewingRate / targetReviewingRate * 100)
                .build();

    }

    public Float getReviewingRate(String partnerDomain, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        partnerCompanyService.validateExistPartnerDomain(partnerDomain);

        List<Long> reviewFks = reservationRepository.findAllReviewFKsByCreatedAtBetween(partnerDomain, startDateTime, endDateTime);

        long todayReservationCount = reviewFks.size();
        if (todayReservationCount == 0) {
            return 0f;
        }

        long todayReviewCount = reviewFks.stream().filter(Objects::nonNull).count();
        return (float) todayReviewCount / todayReservationCount * 100;
    }

    public List<ReviewingAchievementBarChartResponse> getReviewingAchievementBarChart(String partnerDomain) {
        List<ReviewingAchievementBarChartResponse> reviewingAchievementBarChartResponses = new ArrayList<>();

        LocalDateTime endDateTime = LocalDateTime.now();
        ConsoleTimeSeriesUnit achievementTimeSeriesUnit = partnerConsoleConfigService.getAchievementTimeSeriesUnit(partnerDomain);

        float targetReviewingRate = partnerConsoleConfigService.getTargetReviewingRate(partnerDomain);

        for (int i = 0; i < REVIEWING_ACHIEVEMENT_BAR_CHART_HORIZONTAL_AXIS_LENGTH; i++) {
            LocalDateTime startDateTime = getStartDateTimeOfTimeSeriesUnit(endDateTime, achievementTimeSeriesUnit);
            float reviewingRate = getReviewingRate(partnerDomain, startDateTime, endDateTime);

            reviewingAchievementBarChartResponses.add(ReviewingAchievementBarChartResponse.builder()
                    .startDateTime(startDateTime)
                    .endDateTime(endDateTime)
                    .reviewingRate(reviewingRate)
                    .targetReviewingRate(targetReviewingRate)
                    .build());

            endDateTime = startDateTime.minusDays(1);
        }

        return reviewingAchievementBarChartResponses;
    }
}
