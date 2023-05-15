package ru.tinkoff.edu.java.bot.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.bot.Bot;
import ru.tinkoff.edu.java.bot.bot.TelegramRefresherBot;
import ru.tinkoff.edu.java.bot.bot.processor.TelegramMessageProcessor;
import ru.tinkoff.edu.java.bot.bot.processor.UserMessageProcessor;
import ru.tinkoff.edu.java.bot.service.MetricService;
import ru.tinkoff.edu.java.bot.service.UpdatesHandler;
import ru.tinkoff.edu.java.bot.storage.InMemoryLinkStorage;
import ru.tinkoff.edu.java.bot.storage.LinkStorage;

@Configuration
@AllArgsConstructor
public class BotConfiguration {

    private BotProperties properties;

    @Bean
    public Bot transferBot(MetricService metricService) {
        return new TelegramRefresherBot(properties.token(), transferUserMessageProcessor(), metricService);
    }

    @Bean
    public MetricService transferMetricService() {
        return new MetricService();
    }

    @Bean
    public UserMessageProcessor transferUserMessageProcessor() {
        return new TelegramMessageProcessor();
    }

    @Bean
    public LinkStorage transferStorage() {
        return new InMemoryLinkStorage();
    }

    @Bean
    public UpdatesHandler transferUpdatesHandler(Bot bot) {
        return new UpdatesHandler(bot);
    }
}
