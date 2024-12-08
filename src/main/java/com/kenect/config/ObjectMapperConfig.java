package com.kenect.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenect.factory.ObjectMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up the Jackson {@link ObjectMapper}.
 *
 * This class provides a pre-configured {@link ObjectMapper} bean using
 * the {@link ObjectMapperFactory}. The {@link ObjectMapper} is used for
 * serializing and deserializing JSON objects throughout the application.
 */
@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return ObjectMapperFactory.createObjectMapper();
    }
}
