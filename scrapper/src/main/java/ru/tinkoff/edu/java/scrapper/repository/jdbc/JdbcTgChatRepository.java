package ru.tinkoff.edu.java.scrapper.repository.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;

@Repository
@Qualifier("JdbcTgChatRepository")
@Slf4j
@RequiredArgsConstructor
public class JdbcTgChatRepository implements TgChatRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void register(long chatId) {
        String sql = "INSERT INTO CHAT (id) VALUES (?)";
        jdbcTemplate.update(sql, chatId);
        log.info("register new chatid {}", chatId);
    }

    @Override
    public void unregister(long chatId) {
        String sql = "DELETE FROM CHAT WHERE id = ?";
        jdbcTemplate.update(sql, chatId);
        log.info("unregister new chatid {}", chatId);
    }

}
