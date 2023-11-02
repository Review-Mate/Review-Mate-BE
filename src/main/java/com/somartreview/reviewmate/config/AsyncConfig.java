package com.somartreview.reviewmate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncConfig {

    private static final int EXECUTOR_CORE_POOL_SIZE = 50;
    private static final int EXECUTOR_QUEUE_CAPACITY = 5000;
    private static final int EXECUTOR_MAX_POOL_SIZE = Integer.MAX_VALUE;
    private static final String EXECUTOR_THREAD_NAME_PREFIX = "async-task-executor-";

    @Bean
    public Executor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(EXECUTOR_CORE_POOL_SIZE);
        executor.setQueueCapacity(EXECUTOR_QUEUE_CAPACITY);
        executor.setMaxPoolSize(EXECUTOR_MAX_POOL_SIZE);
        executor.setThreadNamePrefix(EXECUTOR_THREAD_NAME_PREFIX);
        executor.initialize();
        return executor;
    }
}
