package ru.tinkoff.edu.java.scrapper.configuration.accessconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jpa.JpaTgChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.jpa.interfaces.JpaChatDao;
import ru.tinkoff.edu.java.scrapper.repository.jpa.interfaces.JpaLinkDao;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaLinkService;
import ru.tinkoff.edu.java.scrapper.service.jpa.JpaTgChatService;

@Configuration
@ConditionalOnProperty(prefix = "app", value = "database-access-type", havingValue = "jpa")
@RequiredArgsConstructor
public class JpaAccessConfiguration {

    private final JpaChatDao jpaChatDao;

    private final JpaLinkDao jpaLinkDao;

    @Bean
    public LinkRepository transferJpaLinkRepository() {
        return new JpaLinkRepository(jpaLinkDao, jpaChatDao);
    }

    @Bean
    public TgChatRepository transferJpaTgChatRepository() {
        return new JpaTgChatRepository(jpaChatDao);
    }

    @Bean
    public LinkService transferJdbcLinkService() {
        return new JpaLinkService(transferJpaLinkRepository(), transferJpaTgChatRepository());
    }

    @Bean
    public TgChatService transferJpaTgChatService() {
        return new JpaTgChatService(transferJpaTgChatRepository());
    }
}
