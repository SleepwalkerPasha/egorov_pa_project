package ru.tinkoff.edu.java.scrapper.repository.jooq;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dto.db.Link;
import ru.tinkoff.edu.java.scrapper.dto.db.LinkInfo;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;

@Repository
@Qualifier("JooqLinkRepository")
public class JooqLinkRepository implements LinkRepository {
    @Override
    public Link add(Link link) {
        return null;
    }

    @Override
    public Link remove(Link link) {
        return null;
    }

    @Override
    public Link update(Link link) {
        return null;
    }

    @Override
    public LinkInfo getLinkInfo(Link link) {
        return null;
    }

    @Override
    public Optional<Link> getLink(Link link) {
        return Optional.empty();
    }

    @Override
    public Collection<Link> findAllLinksById(long tgChatId) {
        return null;
    }

    @Override
    public Collection<Link> findAll() {
        return null;
    }

    @Override
    public Collection<Link> findOldLinks(OffsetDateTime checkedTime) {
        return null;
    }
}
