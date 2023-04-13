package ru.tinkoff.edu.java.scrapper.service.jdbc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.response.Link;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.util.Collection;

@Service
public class JdbcLinkService implements LinkService {

    private final LinkRepository linkRepository;

    public JdbcLinkService(@Qualifier("JdbcLinkRepository") LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public Link add(long tgChatId, URI url) {
        return linkRepository.add(tgChatId, url);
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        return linkRepository.remove(tgChatId, url);
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        return linkRepository.listAll(tgChatId);
    }
}
