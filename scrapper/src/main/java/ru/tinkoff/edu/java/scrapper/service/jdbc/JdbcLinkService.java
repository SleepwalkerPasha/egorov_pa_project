package ru.tinkoff.edu.java.scrapper.service.jdbc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.db.Link;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundException;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@Service
@Qualifier("JdbcLinkService")
public class JdbcLinkService implements LinkService {

    private final LinkRepository linkRepository;

    private final TgChatRepository chatRepository;

    public JdbcLinkService(@Qualifier("JdbcLinkRepository") LinkRepository linkRepository,
                           @Qualifier("JdbcTgChatRepository") TgChatRepository chatRepository) {
        this.linkRepository = linkRepository;
        this.chatRepository = chatRepository;
    }

    @Override
    @Transactional
    public Link add(long tgChatId, AddLinkRequest request) {
        checkTgId(tgChatId);
        Link link = new Link();
        link.setTgId(tgChatId);
        link.setUrl(URI.create(request.link()));
        return linkRepository.add(link);
    }

    @Transactional
    @Override
    public Link remove(long tgChatId, RemoveLinkRequest request) {
        checkTgId(tgChatId);
        Link link = new Link();
        link.setTgId(tgChatId);
        link.setUrl(URI.create(request.link()));
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
        Optional<Long> findId = chatRepository.findByTgChatId(tgChatId);
        if (findId.isEmpty()){
            throw new NotFoundException("данный id не зарегистрирован");
        }
    }
}
