package ru.tinkoff.edu.java.scrapper.service.jpa;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.dto.db.Link;
import ru.tinkoff.edu.java.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.response.GithubResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.Response;
import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowResponse;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundException;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {

    private final LinkRepository jpaLinkRepository;

    private final TgChatRepository jpaTgChatRepository;


    @Override
    public Link add(long tgChatId, AddLinkRequest url, Response response) {
        checkTgId(tgChatId);
        Link link = new Link();
        link.setTgId(tgChatId);
        link.setUrl(URI.create(url.link()));
        if (response instanceof GithubResponse githubResponse) {
            link.setUpdatedAt(githubResponse.pushedAt());
            link.setCheckedAt(OffsetDateTime.now());
            link.setForksCount(githubResponse.forksCount());
            link.setOpenIssuesCount(githubResponse.openIssuesCount());
        } else if (response instanceof StackOverflowResponse stackOverflowResponse) {
            link.setUpdatedAt(stackOverflowResponse.lastActivityDate());
            link.setCheckedAt(OffsetDateTime.now());
            link.setAnswerCount(stackOverflowResponse.answerCount());
            link.setCommentCount(stackOverflowResponse.commentCount());
        } else
            return null;
        return jpaLinkRepository.add(link);
    }

    @Override
    public Link remove(long tgChatId, RemoveLinkRequest url) {
        checkTgId(tgChatId);
        Link link = new Link();
        link.setTgId(tgChatId);
        link.setUrl(URI.create(url.link()));
        return jpaLinkRepository.remove(link);
    }

    @Override
    public Collection<Link> listAllLinksById(long tgChatId) {
        checkTgId(tgChatId);
        return jpaLinkRepository.findAllLinksById(tgChatId);
    }

    @Override
    public Collection<Link> listAll() {
        return jpaLinkRepository.findAll();
    }

    private void checkTgId(long tgChatId) {
        Optional<Long> findId = jpaTgChatRepository.findByTgChatId(tgChatId);
        if (findId.isEmpty()) {
            throw new NotFoundException("данный id не зарегистрирован");
        }
    }
}
