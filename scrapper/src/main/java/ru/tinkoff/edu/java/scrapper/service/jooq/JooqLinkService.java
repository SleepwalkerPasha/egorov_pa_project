package ru.tinkoff.edu.java.scrapper.service.jooq;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.client.GithubClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.dto.db.Link;
import ru.tinkoff.edu.java.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.response.GithubResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowResponse;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundException;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.parser.AbstractParser;
import ru.tinkoff.edu.parser.dto.GithubResult;
import ru.tinkoff.edu.parser.dto.ParseResult;
import ru.tinkoff.edu.parser.dto.StackOverflowResult;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
@Qualifier("JooqLinkService")
public class JooqLinkService implements LinkService {

    private final LinkRepository linkRepository;

    private final TgChatRepository tgChatRepository;

    private final AbstractParser parser;

    private final GithubClient githubClient;

    private final StackOverflowClient stackOverflowClient;


    public JooqLinkService(@Qualifier("JooqLinkRepository") LinkRepository linkRepository,
                           @Qualifier("JooqTgChatRepository") TgChatRepository tgChatRepository,
                           AbstractParser parser,
                           GithubClient githubClient,
                           StackOverflowClient stackOverflowClient) {
        this.linkRepository = linkRepository;
        this.tgChatRepository = tgChatRepository;
        this.parser = parser;
        this.githubClient = githubClient;
        this.stackOverflowClient = stackOverflowClient;
    }

    @Override
    @Transactional
    public Link add(long tgChatId, AddLinkRequest url) {
        checkTgId(tgChatId);
        Link link = new Link();
        link.setTgId(tgChatId);
        link.setUrl(URI.create(url.link()));
        ParseResult parse = parser.parse(URI.create(url.link()));
        if (parse.resultType().equals("GithubResult")) {
            GithubResult result = (GithubResult) parse;
            GithubResponse githubResponse = githubClient.fetchRepository(result.name(), result.repoName());
            link.setUpdatedAt(githubResponse.pushedAt());
            link.setCheckedAt(OffsetDateTime.now());
            link.setForksCount(githubResponse.forksCount());
            link.setOpenIssuesCount(githubResponse.openIssuesCount());
        } else if (parse.resultType().equals("StackOverflowResult")){
            StackOverflowResult stackOverflowResult = (StackOverflowResult) parse;
            StackOverflowResponse stackOverflowResponse = stackOverflowClient.fetchQuestion(stackOverflowResult.id().toString());
            link.setUpdatedAt(stackOverflowResponse.lastActivityDate());
            link.setCheckedAt(OffsetDateTime.now());
            link.setAnswerCount(stackOverflowResponse.answerCount());
            link.setCommentCount(stackOverflowResponse.commentCount());
        } else
            throw new BadRequestException("данные ссылки не поддерживаются");
        return linkRepository.add(link);
    }

    @Override
    @Transactional
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
        if (findId.isEmpty()) {
            throw new NotFoundException("данный id не зарегистрирован");
        }
    }
}
