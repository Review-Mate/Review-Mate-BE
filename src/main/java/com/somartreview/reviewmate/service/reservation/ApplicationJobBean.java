package com.somartreview.reviewmate.service.reservation;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

abstract class ApplicationJobBean extends QuartzJobBean {

    private ApplicationContext applicationContext;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        applicationContext = (ApplicationContext) context.getJobDetail().getJobDataMap().get("applicationContext");
        executeJob(context);
    }

    protected abstract void executeJob(JobExecutionContext context) throws JobExecutionException;

    protected Object getBean(String beanId) {
        return applicationContext.getBean(beanId);
    }
}
