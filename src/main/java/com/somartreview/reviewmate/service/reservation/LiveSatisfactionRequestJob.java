package com.somartreview.reviewmate.service.reservation;

import com.somartreview.reviewmate.domain.reservation.Reservation;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class LiveSatisfactionRequestJob extends ApplicationJobBean {

    @Override
    public void executeJob(JobExecutionContext context) throws JobExecutionException {
        ReservationService reservationService = (ReservationService) super.getBean("reservationService");

        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        long productId = jobDataMap.getLong("productId");
        LocalDateTime productStartLocalDateTime = (LocalDateTime) jobDataMap.get("productStartLocalDateTime");
        log.info("### Trigger LiveSatisfactionRequestJob-" + productId + "-" + productStartLocalDateTime);

        List<Reservation> reservations = reservationService.findByProductIdAndStartDateTime(productId, productStartLocalDateTime);
        reservations.stream().map(Reservation::getCustomer).forEach(customer -> {
            log.info("### LiveSatisfactionRequestJob-" + productId + "-" + productStartLocalDateTime + " | " + customer.getName());
        });
    }
}
