package ru.tinkoff.edu.java.scrapper.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.scrapper.configuration.database.access.config.AccessType;
import ru.tinkoff.edu.java.scrapper.scheduler.Scheduler;


@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfigProperties(@NotNull String test,
                                          @NotNull Scheduler scheduler,
                                          @NotNull AccessType databaseAccessType,
                                          @NotNull String queueName,
                                          @NotNull String exchangeName,
                                          @NotNull String routingKey,
                                          boolean useQueue) {

    @Bean
    public Long getSchedulerIntervalSeconds() {
        return scheduler.interval().toSeconds();
    }
}
