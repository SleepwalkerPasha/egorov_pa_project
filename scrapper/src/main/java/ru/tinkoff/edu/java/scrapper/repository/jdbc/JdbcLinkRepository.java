package ru.tinkoff.edu.java.scrapper.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dto.response.Link;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;

import java.net.URI;
import java.util.Collection;

@Repository
@Qualifier("JdbcLinkRepository")
@RequiredArgsConstructor
public class JdbcLinkRepository implements LinkRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Link add(long tgChatId, URI url) {
        String strUrl = url.toString();
        String sql = "INSERT INTO link (url, tg_id) VALUES (:strUrl, :tgChatId)";
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new Link(URI.create(rs.getString("url")), rs.getLong("tg_id")));
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        String strUrl = url.toString();
        String sql = "DELETE FROM link AS L WHERE L.url = :strUrl AND L.tg_id = :tgChatId";
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new Link(URI.create(rs.getString("url")), rs.getLong("tg_id")));
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        String sql = "SELECT * FROM link WHERE tg_id=:tgChatId";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new Link(URI.create(rs.getString("url")), rs.getLong("tg_id")));
    }
}
