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
import ru.tinkoff.edu.java.scrapper.dto.db.LinkInfo;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
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
        if (getLink(link).isPresent())
            return link;
        String strUrl = link.getUrl().toString();
        String sql = "INSERT INTO link (url, tg_id, checked_at, update_at) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, strUrl);
            preparedStatement.setLong(2, link.getTgId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(link.getCheckedAt().toLocalDateTime()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(link.getUpdatedAt().toLocalDateTime()));
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
        String sql = "UPDATE link SET checked_at = now(), update_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, Timestamp.valueOf(link.getCheckedAt().toLocalDateTime()),
                Timestamp.valueOf(link.getUpdatedAt().toLocalDateTime()),
                link.getId());
        return link;
    }

    @Override
    public Optional<Link> getLink(Link link) {
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query("SELECT * FROM LINK AS L WHERE L.url = ?", linkRowMapper, link.getUrl().toString())
                )
        );
    }

    @Override
    public LinkInfo getLinkInfo(Link link) {
        return DataAccessUtils.singleResult(
                jdbcTemplate.query("SELECT " +
                                "ID, OPEN_ISSUES_COUNT, FORKS_COUNT, ANSWER_COUNT, COMMENT_COUNT FROM LINK AS L WHERE L.url = ?",
                        (rs, rowNum) -> LinkInfo
                                .builder()
                                .id(rs.getLong("id"))
                                .openIssuesCount(rs.getInt("OPEN_ISSUES_COUNT"))
                                .forksCount(rs.getInt("FORKS_COUNT"))
                                .answerCount(rs.getInt("ANSWER_COUNT"))
                                .commentCount(rs.getInt("COMMENT_COUNT"))
                                .build(), link.getUrl().toString())

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
