package ru.tinkoff.edu.java.scrapper.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dto.db.Link;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Repository
@Qualifier("JdbcLinkRepository")
public class JdbcLinkRepository implements LinkRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Link> linkRowMapper;

    @Autowired
    public JdbcLinkRepository(JdbcTemplate jdbcTemplate, RowMapper<Link> linkRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.linkRowMapper = linkRowMapper;
    }

    @Override
    public Link add(Link link) {
        String strUrl = link.getUrl().toString();
        String sql = "INSERT INTO link (url, tg_id, checked_at, update_at) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, strUrl);
            preparedStatement.setLong(2, link.getTgId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(link.getCheckedAt().toString()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(link.getCheckedAt().toString()));
            return preparedStatement;
        }, keyHolder);
        link.setId((Long) Objects.requireNonNull(keyHolder.getKeys()).get("id"));
        return link;
    }


    @Override
    public Link remove(Link link) {
        String strUrl = link.getUrl().toString();
        String sql = "DELETE FROM link AS L WHERE L.url = ? AND L.tg_id = ?";
        jdbcTemplate.update(sql, strUrl, link.getTgId());
        return link;
    }

    public Optional<Link> getLink(Link link) {
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query("SELECT * FROM LINK AS L WHERE L.url = ?", linkRowMapper, link.getUrl().toString())
                )
        );
    }

    @Override
    public Collection<Link> findAllLinksById(long tgChatId) {
        String sql = "SELECT * FROM link WHERE tg_id = ?";
        return jdbcTemplate.query(sql, linkRowMapper, tgChatId);
    }

    @Override
    public Collection<Link> findAll() {
        String sql = "SELECT * FROM link";
        return jdbcTemplate.query(sql, linkRowMapper);
    }
}
