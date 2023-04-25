package ru.tinkoff.edu.java.scrapper.repository.jooq;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.UpdateSetMoreStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.LinkRecord;
import ru.tinkoff.edu.java.scrapper.dto.db.Link;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Optional;

import static ru.tinkoff.edu.java.scrapper.domain.jooq.Tables.LINK;

@Repository
@Qualifier("JooqLinkRepository")
public class JooqLinkRepository implements LinkRepository {

    private final DSLContext jooq;

    @Autowired
    public JooqLinkRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    private Link map(Record record) {
        LinkRecord linkRecord = (LinkRecord) record.get(0);
        return new Link(linkRecord.getValue(LINK.ID),
                URI.create(linkRecord.getValue(LINK.URL)),
                linkRecord.getValue(LINK.TG_ID),
                linkRecord.getValue(LINK.CHECKED_AT).atOffset(ZoneOffset.UTC),
                linkRecord.getValue(LINK.UPDATE_AT).atOffset(ZoneOffset.UTC),
                linkRecord.getValue(LINK.FORKS_COUNT),
                linkRecord.getValue(LINK.ANSWER_COUNT),
                linkRecord.getValue(LINK.OPEN_ISSUES_COUNT),
                linkRecord.getValue(LINK.COMMENT_COUNT));
    }

    @Override
    public Link add(Link link) {
        if (getLink(link).isPresent())
            return link;
        long id = jooq.insertInto(LINK, LINK.URL, LINK.UPDATE_AT, LINK.CHECKED_AT, LINK.TG_ID,
                        LINK.OPEN_ISSUES_COUNT, LINK.FORKS_COUNT, LINK.ANSWER_COUNT, LINK.COMMENT_COUNT)
                .values(link.getUrl().toString(),
                        link.getUpdatedAt().toLocalDateTime(),
                        link.getCheckedAt().toLocalDateTime(),
                        link.getTgId(),
                        link.getOpenIssuesCount(),
                        link.getForksCount(),
                        link.getAnswerCount(),
                        link.getCommentCount())
                .returningResult(LINK.ID)
                .fetchOne()
                .map(record -> record.get(LINK.ID));
        link.setId(id);
        return link;
    }

    @Override
    public Link remove(Link link) {
        jooq.delete(LINK)
                .where(LINK.URL.eq(link.getUrl().toString()))
                .and(LINK.TG_ID.eq(link.getTgId()))
                .execute();
        return link;
    }

    @Override
    public Link update(Link link) {
        UpdateSetMoreStep<LinkRecord> set = jooq.update(LINK)
                .set(LINK.CHECKED_AT, OffsetDateTime.now().toLocalDateTime())
                .set(LINK.UPDATE_AT, link.getUpdatedAt().toLocalDateTime());

        if (link.getOpenIssuesCount() == null)
            set = set.setNull(LINK.OPEN_ISSUES_COUNT);
        else
            set = set.set(LINK.OPEN_ISSUES_COUNT, link.getOpenIssuesCount());
        if (link.getForksCount() == null)
            set = set.setNull(LINK.FORKS_COUNT);
        else
            set = set.set(LINK.FORKS_COUNT, link.getForksCount());
        if (link.getAnswerCount() == null)
            set = set.setNull(LINK.ANSWER_COUNT);
        else
            set = set.set(LINK.ANSWER_COUNT, link.getAnswerCount());
        if (link.getCommentCount() == null)
            set = set.setNull(LINK.COMMENT_COUNT);
        else
            set = set.set(LINK.COMMENT_COUNT, link.getCommentCount());

        set.where(LINK.ID.eq(link.getId())).execute();
        return link;
    }


    @Override
    public Optional<Link> getLink(Link link) {
        return jooq.select(LINK)
                .from(LINK)
                .where(LINK.URL.eq(link.getUrl().toString()))
                .fetch()
                .map(this::map).stream().findFirst();
    }

    @Override
    public Collection<Link> findAllLinksById(long tgChatId) {
        return jooq.select(LINK)
                .from(LINK)
                .where(LINK.TG_ID.eq(tgChatId))
                .fetch()
                .map(this::map);
    }

    @Override
    public Collection<Link> findAll() {
        return jooq.select(LINK)
                .from(LINK)
                .fetch()
                .map(this::map);
    }

    @Override
    public Collection<Link> findOldLinks(OffsetDateTime checkedTime) {
        return jooq.select(LINK)
                .from(LINK)
                .where(LINK.CHECKED_AT.lessOrEqual(checkedTime.toLocalDateTime()))
                .fetch()
                .map(this::map);
    }

}
