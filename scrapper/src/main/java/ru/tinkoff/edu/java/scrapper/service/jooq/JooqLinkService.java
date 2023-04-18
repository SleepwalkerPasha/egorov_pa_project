package ru.tinkoff.edu.java.scrapper.service.jooq;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.db.Link;
import ru.tinkoff.edu.java.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundException;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@Service
@Qualifier("JooqLinkService")
public class JooqLinkService implements LinkService {

    private final LinkRepository linkRepository;

    private final TgChatRepository tgChatRepository;

    public JooqLinkService(@Qualifier("JooqLinkRepository") LinkRepository linkRepository,
                           @Qualifier("JooqTgChatRepository") TgChatRepository tgChatRepository) {
        this.linkRepository = linkRepository;
        this.tgChatRepository = tgChatRepository;
    }

    @Override
    public Link add(long tgChatId, AddLinkRequest url) {
        checkTgId(tgChatId);
        Link link = new Link();
        link.setTgId(tgChatId);
        link.setUrl(URI.create(url.link()));
        return linkRepository.add(link);
    }

    @Override
    public Link remove(long tgChatId, RemoveLinkRequest url) {
        checkTgId(tgChatId);
        Link link = new Link();
        link.setTgId(tgChatId);
        link.setUrl(URI.create(url.link()));
        return linkRepository.remove(link);
    }

    @Override
    public Collection<Link> listAllLinksById(long tgChatId) {
        checkTgId(tgChatId);
        return linkRepository.findAllLinksById(tgChatId);
    }

    @Override
    public Collection<Link> listAll() {
        return linkRepository.findAll();
    }

    private void checkTgId(long tgChatId) {
        Optional<Long> findId = tgChatRepository.findByTgChatId(tgChatId);
        if (findId.isEmpty()){
            throw new NotFoundException("данный id не зарегистрирован");
        }
    }
}
