package ru.tinkoff.edu.java.scrapper.repository;

import ru.tinkoff.edu.java.scrapper.dto.db.Link;

import java.util.Collection;

public interface LinkRepository {

    Link add(Link link);

    Link remove(Link link);

    Collection<Link> findAllLinksById(long tgChatId);

    Collection<Link> findAll();
}
