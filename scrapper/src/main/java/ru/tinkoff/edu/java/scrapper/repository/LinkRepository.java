package ru.tinkoff.edu.java.scrapper.repository;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;
import ru.tinkoff.edu.java.scrapper.dto.db.Link;

public interface LinkRepository {

    Link add(Link link);

    Link remove(Link link);

    Link update(Link link);

    Optional<Link> getLink(Link link);

    Collection<Link> findAllLinksById(long tgChatId);

    Collection<Link> findAll();

    Collection<Link> findOldLinks(OffsetDateTime checkedTime);
}
