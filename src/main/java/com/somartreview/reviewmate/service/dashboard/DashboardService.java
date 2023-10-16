package com.somartreview.reviewmate.service.dashboard;

import com.somartreview.reviewmate.domain.reservation.ReservationRepository;
import com.somartreview.reviewmate.domain.review.ReviewRepository;
import com.somartreview.reviewmate.service.partners.PartnerCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;
    private final PartnerCompanyService partnerCompanyService;


    public Float getReviewingRate(String partnerDomain, TimeSeriesUnit timeSeriesUnit) {
        partnerCompanyService.validateExistingPartnerDomain(partnerDomain);

        LocalDateTime countingStartDateTime = getCountingStartDateTime(timeSeriesUnit);
        List<Long> reviewFks = reservationRepository.findAllReviewFKsByCreatedAtGreaterThanEqual(partnerDomain, countingStartDateTime);

        long todayReservationCount = reviewFks.size();
        if (todayReservationCount == 0) {
            return 0f;
        }

        long todayReviewCount = reviewFks.stream().filter(Objects::nonNull).count();
        return (float) todayReviewCount / todayReservationCount * 100;
    }

    private LocalDateTime getCountingStartDateTime(TimeSeriesUnit timeSeriesUnit) {
        LocalDateTime now = LocalDateTime.now();
        return switch (timeSeriesUnit) {
            case DAILY -> LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);
            case WEEKLY -> now.with(DayOfWeek.MONDAY);
            case MONTHLY -> LocalDateTime.of(now.getYear(), now.getMonth(), 1, 0, 0, 0);
        };
    }

    public Long getTotalReviewCount(String partnerDomain) {
        partnerCompanyService.validateExistingPartnerDomain(partnerDomain);

        return reviewRepository.countByPartnerDomain(partnerDomain);
    }
}
