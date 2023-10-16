package com.somartreview.reviewmate.service;

import com.somartreview.reviewmate.domain.reservation.ReservationRepository;
import com.somartreview.reviewmate.service.partners.PartnerCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ReservationRepository reservationRepository;
    private final PartnerCompanyService partnerCompanyService;


    public Float getDailyReviewingRate(String partnerDomain) {
        partnerCompanyService.validateExistingPartnerDomain(partnerDomain);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayStart = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);
        List<Long> reviewFks = reservationRepository.findAllReviewFKsByCreatedAtGreaterThanEqual(partnerDomain, todayStart);

        long todayReservationCount = reviewFks.size();
        if (todayReservationCount == 0) {
            return 0f;
        }

        long todayReviewCount = reviewFks.stream().filter(Objects::nonNull).count();
        return (float) todayReviewCount / todayReservationCount * 100;
    }
}
