package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "client", ignoreUnknownFields = false)
public record ClientConfigProperties(String githubUrl, String stackOverflowUrl, String botUrl, Long daysOffset) {

    @Bean
    public String githubUrl() {
        return githubUrl;
    }

    @Bean
    public String stackOverflowUrl() {
        return stackOverflowUrl;
    }

    @Bean
    public String botUrl() {
        return botUrl;
    }

}
