package ru.tinkoff.edu.java.scrapper.configuration.message.access.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.configuration.ClientConfigProperties;
import ru.tinkoff.edu.java.scrapper.message.sender.BotWebClient;
import ru.tinkoff.edu.java.scrapper.message.sender.LinkUpdateSender;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
@RequiredArgsConstructor
public class SyncAccessConfiguration {

    private final ClientConfigProperties configProperties;

    @Bean
    public LinkUpdateSender transferBotClient() {
        return new BotWebClient(WebClient.builder(), configProperties.botUrl());
    }
}
