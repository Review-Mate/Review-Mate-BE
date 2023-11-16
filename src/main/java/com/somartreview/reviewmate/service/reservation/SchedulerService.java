package com.somartreview.reviewmate.service.reservation;

import com.somartreview.reviewmate.exception.ReviewMateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static com.somartreview.reviewmate.exception.ErrorCode.SCHEDULER_REGISTER_ERROR;

@Slf4j
@Service
public class SchedulerService {

    private final ApplicationContext applicationContext;
    private final StdSchedulerFactory stdSchedulerFactory;

    @Autowired
    public SchedulerService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.stdSchedulerFactory = new StdSchedulerFactory();
    }

    public void registerLiveSatisfactionRequest(Long productId, LocalDateTime productStartLocalDateTime) {
        try {
            Scheduler scheduler = stdSchedulerFactory.getScheduler();
            scheduler.start();

            Instant instant = productStartLocalDateTime.atZone(ZoneId.systemDefault()).toInstant();
            Date productStartDate = Date.from(instant);

            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("productId", productId);
            jobDataMap.put("productStartLocalDateTime", productStartLocalDateTime);

            // overlap

            JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
            factoryBean.setJobClass(LiveSatisfactionRequestJob.class);
            factoryBean.setName("LiveSatisfactionRequestJob-" + productId + "-" + productStartDate);
            factoryBean.setJobDataMap(jobDataMap);
            factoryBean.setApplicationContext(applicationContext);
            factoryBean.setApplicationContextJobDataKey("applicationContext");
            factoryBean.afterPropertiesSet();
            JobDetail jobDetail = factoryBean.getObject();

            SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder
                    .newTrigger()
                    .withIdentity("LiveSatisfactionRequestTrigger-" + productId + "-" + productStartDate)
                    .startAt(productStartDate)
                    .forJob("LiveSatisfactionRequestJob-" + productId + "-" + productStartDate)
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            log.info("### Register LiveSatisfactionRequestJob-" + productId + "-" + productStartDate);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new ReviewMateException(SCHEDULER_REGISTER_ERROR);
        }
    }
}
