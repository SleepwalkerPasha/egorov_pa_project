package ru.tinkoff.edu.java.scrapper.configuration.accessconfig;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;
import ru.tinkoff.edu.java.scrapper.service.jooq.JooqLinkService;
import ru.tinkoff.edu.java.scrapper.service.jooq.JooqTgChatService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
@RequiredArgsConstructor
public class JooqAccessConfiguration {

    private final DSLContext context;

    @Bean
    public LinkRepository transferJooqLinkRepository() {
        return new JooqLinkRepository(context);
    }

    @Bean
    public TgChatRepository transferJooqTgChatRepository() {
        return new JooqTgChatRepository(context);
    }

    @Bean
    public LinkService transferJdbcLinkService() {
        return new JooqLinkService(transferJooqLinkRepository(), transferJooqTgChatRepository());
    }

    @Bean
    public TgChatService transferJooqTgChatService() {
        return new JooqTgChatService(transferJooqTgChatRepository());
    }
}
