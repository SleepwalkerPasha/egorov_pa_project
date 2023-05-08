package ru.tinkoff.edu.java.scrapper.service;

import java.util.Collection;
import ru.tinkoff.edu.java.common.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.common.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.db.Link;
import ru.tinkoff.edu.java.scrapper.dto.response.Response;

public interface LinkService {
    Link add(long tgChatId, AddLinkRequest url, Response response);

    Link remove(long tgChatId, RemoveLinkRequest url);

    Collection<Link> listAllLinksById(long tgChatId);

    Collection<Link> listAll();

}
