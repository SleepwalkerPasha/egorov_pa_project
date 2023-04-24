package ru.tinkoff.edu.java.scrapper.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dto.db.Link;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcLinkRepository implements LinkRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Link> linkRowMapper;

    @Override
    @Transactional
    public Link add(Link link) {
        if (getLink(link).isPresent())
            return link;
        String strUrl = link.getUrl().toString();
        String sql = "INSERT INTO link " +
                "(url, tg_id, checked_at, update_at, open_issues_count, forks_count, answer_count, comment_count) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, strUrl);
            preparedStatement.setLong(2, link.getTgId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(link.getCheckedAt().toLocalDateTime()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(link.getUpdatedAt().toLocalDateTime()));
            if (link.getOpenIssuesCount() == null)
                preparedStatement.setNull(5, Types.INTEGER);
            else
                preparedStatement.setInt(5, link.getOpenIssuesCount());
            if (link.getForksCount() == null)
                preparedStatement.setNull(6, Types.INTEGER);
            else
                preparedStatement.setInt(6, link.getForksCount());
            if (link.getAnswerCount() == null)
                preparedStatement.setNull(7, Types.INTEGER);
            else
                preparedStatement.setInt(7, link.getAnswerCount());
            if (link.getCommentCount() == null)
                preparedStatement.setNull(8, Types.INTEGER);
            else
                preparedStatement.setInt(8, link.getCommentCount());
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

    @Override
    public Link update(Link link) {
        String sql = "UPDATE link SET checked_at = now(), update_at = ?, open_issues_count = ?, " +
                "forks_count = ?, answer_count = ?, comment_count = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                Timestamp.valueOf(link.getUpdatedAt().toLocalDateTime()),
                link.getOpenIssuesCount(),
                link.getForksCount(),
                link.getAnswerCount(),
                link.getCommentCount(),
                link.getId());
        return link;
    }

    @Override
    public Optional<Link> getLink(Link link) {
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query("SELECT * FROM link AS L WHERE L.url = ?", linkRowMapper, link.getUrl().toString())
                )
        );
    }

    @Override
    public Collection<Link> findAllLinksById(long tgChatId) {
        String sql = "SELECT * FROM link WHERE tg_id = ?";
        return jdbcTemplate.query(sql, linkRowMapper, tgChatId);
    }

    @Override
    public Collection<Link> findOldLinks(OffsetDateTime checkedTime) {
        String sql = "SELECT * FROM link WHERE checked_at <= ?";
        return jdbcTemplate.query(sql, linkRowMapper, checkedTime);
    }

    @Override
    public Collection<Link> findAll() {
        String sql = "SELECT * FROM link";
        return jdbcTemplate.query(sql, linkRowMapper);
    }

}
