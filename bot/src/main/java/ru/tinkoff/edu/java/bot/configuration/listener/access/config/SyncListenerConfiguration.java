package ru.tinkoff.edu.java.bot.configuration.listener.access.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import ru.tinkoff.edu.java.bot.configuration.BotConfiguration;
import ru.tinkoff.edu.java.bot.service.Listener;
import ru.tinkoff.edu.java.bot.service.SyncListener;

@RequiredArgsConstructor
public class SyncListenerConfiguration {

    private final BotConfiguration botConfiguration;

    @Bean
    public Listener transferReceiver() {
        return new SyncListener(botConfiguration.transferUpdatesHandler());
    }
}
