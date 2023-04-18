package ru.tinkoff.edu.java.scrapper.repository.jooq;

import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.LinkRecord;
import ru.tinkoff.edu.java.scrapper.dto.db.Link;
import ru.tinkoff.edu.java.scrapper.dto.db.LinkInfo;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Link.LINK;

@Repository
@Qualifier("JooqLinkRepository")
public class JooqLinkRepository implements LinkRepository {

    private final DSLContext jooq;

    @Autowired
    public JooqLinkRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    @Transactional
    public Link add(Link link) {
        if (getLink(link).isPresent())
            return link;
        long id = Objects.requireNonNull(jooq.insertInto(LINK, LINK.URL, LINK.UPDATE_AT, LINK.CHECKED_AT, LINK.TG_ID)
                .values(link.getUrl().toString(),
                        link.getUpdatedAt().toLocalDateTime(),
                        link.getCheckedAt().toLocalDateTime(),
                        link.getTgId())
                .returningResult(LINK.ID)
                .fetchOne()
        ).map(record -> record.get(LINK.ID));
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
        jooq.update(LINK)
                .set(LINK.CHECKED_AT, OffsetDateTime.now().toLocalDateTime())
                .set(LINK.UPDATE_AT, link.getUpdatedAt().toLocalDateTime())
                .where(LINK.ID.eq(link.getId()))
                .execute();
        return link;
    }

    @Override
    public LinkInfo getLinkInfo(Link link) {
        return jooq.select(LINK.ID, LINK.OPEN_ISSUES_COUNT, LINK.FORKS_COUNT, LINK.ANSWER_COUNT, LINK.COMMENT_COUNT)
                .from(LINK)
                .where(LINK.URL.eq(link.getUrl().toString()))
                .fetch()
                .map(record -> {
                    LinkRecord linkRecord = (LinkRecord) record;
                    return LinkInfo.builder().id(linkRecord.getValue(LINK.ID))
                            .forksCount(linkRecord.getValue(LINK.FORKS_COUNT))
                            .answerCount(linkRecord.getValue(LINK.ANSWER_COUNT))
                            .openIssuesCount(linkRecord.getValue(LINK.OPEN_ISSUES_COUNT))
                            .commentCount(linkRecord.getValue(LINK.COMMENT_COUNT))
                            .build();
                })
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Optional<Link> getLink(Link link) {
        return jooq.select(LINK)
                .from(LINK)
                .where(LINK.URL.eq(link.getUrl().toString()))
                .fetch()
                .map(mapper()).stream().findFirst();
    }

    @Override
    public Collection<Link> findAllLinksById(long tgChatId) {
        return jooq.select(LINK)
                .where(LINK.TG_ID.eq(tgChatId))
                .fetch()
                .map(mapper());
    }

    @Override
    public Collection<Link> findAll() {
        return jooq.select(LINK)
                .fetch()
                .map(mapper());
    }

    @Override
    public Collection<Link> findOldLinks(OffsetDateTime checkedTime) {
        return jooq.select(LINK)
                .where(LINK.CHECKED_AT.lessOrEqual(checkedTime.toLocalDateTime()))
                .fetch()
                .map(mapper());
    }

    private RecordMapper<Record1<LinkRecord>, Link> mapper() {
        return record1 -> {
            LinkRecord linkRecord = (LinkRecord) record1.get(0);
            return new Link(linkRecord.getValue(LINK.ID),
                    URI.create(linkRecord.getValue(LINK.URL)),
                    linkRecord.getValue(LINK.TG_ID),
                    linkRecord.getValue(LINK.CHECKED_AT).atOffset(ZoneOffset.UTC),
                    linkRecord.getValue(LINK.UPDATE_AT).atOffset(ZoneOffset.UTC));
        };
    }
}
