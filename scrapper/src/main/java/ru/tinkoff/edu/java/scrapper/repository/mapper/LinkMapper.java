package ru.tinkoff.edu.java.scrapper.repository.mapper;

import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.dto.db.Link;

@Component
public class LinkMapper implements RowMapper<Link> {
    @Override
    public Link mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Link(rs.getLong("id"),
                URI.create(rs.getString("url")),
                rs.getLong("tg_id"),
                rs.getTimestamp("checked_at").toLocalDateTime().atOffset(ZoneOffset.UTC),
                rs.getTimestamp("update_at").toLocalDateTime().atOffset(ZoneOffset.UTC),
                rs.getInt("forks_count"),
                rs.getInt("answer_count"),
                rs.getInt("open_issues_count"),
                rs.getInt("comment_count"));
    }
}
