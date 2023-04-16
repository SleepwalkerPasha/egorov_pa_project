package ru.tinkoff.edu.java.scrapper.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.tinkoff.edu.java.scrapper.updater.LinkUpdater;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Configuration
@EnableScheduling
public class LinkUpdateScheduler {

    private final LinkUpdater linkUpdater;

    public LinkUpdateScheduler(LinkUpdater linkUpdater) {
        this.linkUpdater = linkUpdater;
    }

    @Scheduled(fixedDelayString = "#{@getSchedulerIntervalSeconds}")
    public void update() {
        linkUpdater.update();
        System.out.println("обновили данные в БД" + OffsetDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        log.info("обновили данные в БД");
    }
}
