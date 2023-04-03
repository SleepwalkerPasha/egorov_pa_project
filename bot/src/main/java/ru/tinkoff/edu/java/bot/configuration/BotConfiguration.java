package ru.tinkoff.edu.java.bot.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.service.Bot;
import ru.tinkoff.edu.java.bot.service.TelegramRefresherBot;
import ru.tinkoff.edu.java.bot.service.processor.TelegramMessageProcessor;
import ru.tinkoff.edu.java.bot.service.processor.UserMessageProcessor;
import ru.tinkoff.edu.java.bot.storage.InMemoryLinkStorage;
import ru.tinkoff.edu.java.bot.storage.LinkStorage;

@Configuration
@AllArgsConstructor
public class BotConfiguration {

    private BotProperties properties;

    @Bean
    public Bot transferBot() {
        return new TelegramRefresherBot(properties.token(), transferUserMessageProcessor());
    }

    @Bean
    public UserMessageProcessor transferUserMessageProcessor() {
        return new TelegramMessageProcessor(transferStorage());
    }

    @Bean
    public LinkStorage transferStorage() {
        return new InMemoryLinkStorage();
    }
}
