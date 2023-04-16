package ru.tinkoff.edu.java.scrapper.service.jooq;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.db.Link;
import ru.tinkoff.edu.java.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.util.Collection;

@Service
@Qualifier("JooqLinkService")
public class JooqLinkService implements LinkService {
    @Override
    public Link add(long tgChatId, AddLinkRequest url) {
        return null;
    }

    @Override
    public Link remove(long tgChatId, RemoveLinkRequest url) {
        return null;
    }

    @Override
    public Collection<Link> listAllLinksById(long tgChatId) {
        return null;
    }

    @Override
    public Collection<Link> listAll() {
        return null;
    }
}
