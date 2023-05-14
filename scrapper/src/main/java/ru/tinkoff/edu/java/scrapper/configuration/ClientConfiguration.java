package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.client.GithubClient;
import ru.tinkoff.edu.java.scrapper.client.GithubWebClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowWebClient;
import ru.tinkoff.edu.parser.AbstractParser;
import ru.tinkoff.edu.parser.GithubParser;
import ru.tinkoff.edu.parser.StackOverflowParser;

@Validated
@Configuration
public class ClientConfiguration {

    private final ClientConfigProperties properties;

    public ClientConfiguration(ClientConfigProperties properties) {
        this.properties = properties;
    }

    @Bean
    public GithubClient transferGithubClient() {
        return GithubWebClient.getInstance(WebClient.builder(), properties.githubUrl());
    }

    @Bean
    public StackOverflowClient transferStackOverflowClient() {
        return StackOverflowWebClient.getInstance(WebClient.builder(), properties.stackOverflowUrl());
    }


    @Bean
    public Long daysOffset() {
        return properties.daysOffset();
    }

    @Bean
    public AbstractParser transferParser() {
        return AbstractParser.of(new GithubParser(), new StackOverflowParser());
    }
}
