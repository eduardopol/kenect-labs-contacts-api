package com.kenect.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenect.factory.ObjectMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return ObjectMapperFactory.createObjectMapper();
    }
}
