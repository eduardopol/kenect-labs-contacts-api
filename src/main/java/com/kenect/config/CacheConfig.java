package com.kenect.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Configuration class for setting up caching using Caffeine.
 *
 * This class configures a {@link CacheManager} that uses Caffeine as the caching provider.
 * The cache is set up to store a maximum of 100 entries, with entries expiring 20 minutes
 * after they are written.
 */
@Configuration
public class CacheConfig {

    /**
     * Configures the {@link CacheManager} bean.
     *
     * This method creates and configures a {@link CaffeineCacheManager} with a cache
     * named "contacts". The cache is configured to:
     *
     *     Expire entries 20 minutes after they are written.
     *     Hold a maximum of 100 entries.
     *
     * @return a configured {@link CacheManager} instance.
     */
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("contacts", "contactsAsync");
        cacheManager.setCaffeine(
            Caffeine.newBuilder()
                .expireAfterWrite(20, TimeUnit.MINUTES)
                .maximumSize(100)
        );
        return cacheManager;
    }
}