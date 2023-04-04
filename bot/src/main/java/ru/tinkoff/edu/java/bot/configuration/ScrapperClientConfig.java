package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.bot.client.ScrapperWebClient;

@Validated
@ConfigurationProperties(prefix = "scrapper", ignoreUnknownFields = false)
public record ScrapperClientConfig(String url) {

    @Bean
    public ScrapperWebClient transferScrapperWebClient() {
        return new ScrapperWebClient(WebClient.builder(), url);
    }
}
