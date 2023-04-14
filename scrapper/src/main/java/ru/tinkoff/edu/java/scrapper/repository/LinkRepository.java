package ru.tinkoff.edu.java.scrapper.repository;

import ru.tinkoff.edu.java.scrapper.dto.db.Link;

import java.time.OffsetDateTime;
import java.util.Collection;

public interface LinkRepository {

    Link add(Link link);

    Link remove(Link link);

    Link update(Link link);

    Collection<Link> findAllLinksById(long tgChatId);

    Collection<Link> findAll();

    Collection<Link> findOldLinks(OffsetDateTime checkedTime);
}
