package ru.tinkoff.edu.java.scrapper.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.OffsetDateTime;

@Slf4j
@Configuration
@EnableScheduling
public class LinkUpdateScheduler {

    @Scheduled(fixedDelayString = "#{@getSchedulerIntervalMilliSeconds}")
    public void update() {
        System.out.println("обновили данные в БД" + OffsetDateTime.now());
        log.info("обновили данные в БД");
    }
}
