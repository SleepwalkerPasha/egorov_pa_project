package ru.tinkoff.edu.java.scrapper.service;

import ru.tinkoff.edu.java.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.db.Link;

import java.util.Collection;

public interface LinkService {
    Link add(long tgChatId, AddLinkRequest url);

    Link remove(long tgChatId, RemoveLinkRequest url);

    Collection<Link> listAllLinksById(long tgChatId);

    Collection<Link> listAll();

}
