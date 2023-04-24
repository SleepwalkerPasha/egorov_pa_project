package ru.tinkoff.edu.java.scrapper.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundException;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;

import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcTgChatRepository implements TgChatRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    @Override
    public long add(long chatId) {
        Optional<Long> id = findByTgChatId(chatId);
        if (id.isPresent())
            throw new BadRequestException("данный пользователь уже зарегистрирован");
        String sql = "INSERT INTO CHAT (id) VALUES (?)";
        if (jdbcTemplate.update(sql, chatId) == 1)
            return chatId;
        else
            return -1L;
    }

    @Override
    @Transactional
    public long remove(long chatId) {
        Optional<Long> id = findByTgChatId(chatId);
        if (id.isEmpty())
            throw new NotFoundException("данный пользователь не зарегистрирован");
        String sql = "DELETE FROM CHAT WHERE id = ?";
        if (jdbcTemplate.update(sql, chatId) == 1)
            return chatId;
        else
            return -1L;
    }

    @Override
    public Collection<Long> findAll() {
        var sql = "SELECT * FROM CHAT";
        return jdbcTemplate.query(sql, tgChatRowMapper());
    }

    @Override
    public Collection<Long> findByLinkId(long id) {
        var sql = "SELECT l.tg_id FROM link AS l LEFT JOIN chat c on c.id = l.tg_id WHERE l.id = ?";
        return jdbcTemplate.query(sql, tgChatRowMapper(), id);
    }

    @Override
    public Optional<Long> findByTgChatId(long id) {
        var sql = "SELECT * FROM chat WHERE id = ?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(sql, tgChatRowMapper(), id)
                )
        );
    }


    private RowMapper<Long> tgChatRowMapper() {
        return ((rs, rowNum) -> rs.getLong("id"));
    }

}
