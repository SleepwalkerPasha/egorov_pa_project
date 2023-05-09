package ru.tinkoff.edu.java.bot.configuration.listener.access.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import ru.tinkoff.edu.java.bot.service.Listener;
import ru.tinkoff.edu.java.bot.service.SyncListener;
import ru.tinkoff.edu.java.bot.service.UpdatesHandler;

@RequiredArgsConstructor
public class SyncListenerConfiguration {

    @Bean
    public Listener transferReceiver(UpdatesHandler updatesHandler) {
        return new SyncListener(updatesHandler);
    }
}
