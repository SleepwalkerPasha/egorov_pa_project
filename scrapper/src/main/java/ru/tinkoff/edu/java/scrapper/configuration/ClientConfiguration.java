package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.client.GithubWebClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowWebClient;

@Validated
@ConfigurationProperties(prefix = "client", ignoreUnknownFields = false)
public record ClientConfiguration(String githubUrl, String stackOverflowUrl) {

    @Bean
    public GithubWebClient transferGithubWebClient() {
        return GithubWebClient.getInstance(WebClient.builder(), githubUrl);
    }

    @Bean
    public StackOverflowWebClient transferStackOverflowWebClient() {
        return StackOverflowWebClient.getInstance(WebClient.builder(), stackOverflowUrl);
    }
}
