package ru.tinkoff.edu.java.scrapper.configuration.accessconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.edu.java.scrapper.dto.db.Link;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.jdbc.JdbcTgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcLinkService;
import ru.tinkoff.edu.java.scrapper.service.jdbc.JdbcTgChatService;


@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
@RequiredArgsConstructor
public class JdbcAccessConfiguration {

    private final RowMapper<Link> linkRowMapper;

    private final JdbcTemplate jdbcTemplate;

    @Bean
    public LinkRepository transferJdbcLinkRepository() {
        return new JdbcLinkRepository(jdbcTemplate, linkRowMapper);
    }

    @Bean
    public TgChatRepository transferJdbcTgChatRepository() {
        return new JdbcTgChatRepository(jdbcTemplate);
    }

    @Bean
    public LinkService transferJdbcLinkService() {
        return new JdbcLinkService(transferJdbcLinkRepository(), transferJdbcTgChatRepository());
    }

    @Bean
    public TgChatService transferJdbcTgChatService() {
        return new JdbcTgChatService(transferJdbcTgChatRepository());
    }
}
