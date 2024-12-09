package com.kenect.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Configuration class for enabling and customizing asynchronous processing.
 */
@Configuration
public class AsyncConfig {

    @Value("${kenect.async.core-pool-size:20}")
    private int corePoolSize;

    @Value("${kenect.async.max-pool-size:100}")
    private int maxPoolSize;

    @Value("${kenect.async.queue-capacity:1000}")
    private int queueCapacity;

    /**
     * Defines a custom thread pool executor for asynchronous processing.
     *
     * @return a configured {@link Executor} for handling async tasks.
     */
    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("AsyncThread-");
        executor.initialize();
        return executor;
    }
}