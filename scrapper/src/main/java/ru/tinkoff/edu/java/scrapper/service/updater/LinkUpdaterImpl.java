package ru.tinkoff.edu.java.scrapper.service.updater;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.client.BotClient;
import ru.tinkoff.edu.java.scrapper.client.GithubClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.dto.db.Link;
import ru.tinkoff.edu.java.scrapper.dto.request.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.dto.response.GithubResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowResponse;
import ru.tinkoff.edu.java.scrapper.repository.LinkRepository;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;
import ru.tinkoff.edu.parser.AbstractParser;
import ru.tinkoff.edu.parser.dto.GithubResult;
import ru.tinkoff.edu.parser.dto.ParseResult;
import ru.tinkoff.edu.parser.dto.StackOverflowResult;

import java.time.OffsetDateTime;
import java.util.List;


@Service
public class LinkUpdaterImpl implements LinkUpdater {

    private final GithubClient githubClient;

    private final StackOverflowClient stackOverflowClient;

    private final BotClient botClient;

    private final LinkRepository linkRepository;

    private final TgChatRepository tgChatRepository;

    private final Long daysOffset;

    private final AbstractParser parser;

    public LinkUpdaterImpl(GithubClient githubClient,
                           StackOverflowClient stackOverflowClient,
                           BotClient botClient,
                           LinkRepository linkRepository,
                           TgChatRepository tgChatRepository,
                           Long daysOffset,
                           AbstractParser parser) {
        this.githubClient = githubClient;
        this.stackOverflowClient = stackOverflowClient;
        this.botClient = botClient;
        this.linkRepository = linkRepository;
        this.tgChatRepository = tgChatRepository;
        this.daysOffset = daysOffset;
        this.parser = parser;
    }


    @Override
    @Transactional
    public int update() {
        int updateResult = 0;
        List<Link> oldLinks = linkRepository.findOldLinks(OffsetDateTime.now().minusDays(daysOffset)).stream().toList();
        ParseResult parseResult;
        for (Link oldLink : oldLinks) {
            parseResult = parser.parse(oldLink.getUrl());
            updateResult += checkUpdates(parseResult, oldLink);
        }
        return updateResult;
    }

    private int checkUpdates(ParseResult parseResult, Link link) {
        int updateResult = 0;
        switch (parseResult.resultType()) {
            case "StackOverflowResult" -> updateResult = checkStackOverflow((StackOverflowResult) parseResult, link);
            case "GithubResult" -> updateResult = checkGithub((GithubResult) parseResult, link);
        }
        return updateResult;
    }

    private int checkStackOverflow(StackOverflowResult parseResult, Link link) {
        Integer questionId = parseResult.id();
        StackOverflowResponse stackOverflowResponse = stackOverflowClient.fetchQuestion(questionId.toString());
        if (link.getUpdatedAt().isBefore(stackOverflowResponse.lastActivityDate())) {
            List<Long> chatIds = tgChatRepository.findByLink(link.getId()).stream().toList();
            link.setUpdatedAt(stackOverflowResponse.lastActivityDate());
            linkRepository.update(link);
            botClient.linkUpdate(LinkUpdateRequest.builder()
                    .id(link.getId())
                    .description("StackOverflow question update")
                    .url(link.getUrl().toString())
                    .tgChatIds(chatIds)
                    .build());
            return chatIds.size();
        }
        return 0;
    }

    private int checkGithub(GithubResult parseResult, Link link) {
        String repoName = parseResult.repoName();
        String username = parseResult.name();
        GithubResponse githubResponse = githubClient.fetchRepository(username, repoName);
        if (link.getUpdatedAt().isBefore(githubResponse.updatedAt())) {
            List<Long> chatIds = tgChatRepository.findByLink(link.getId()).stream().toList();
            link.setUpdatedAt(githubResponse.updatedAt());
            linkRepository.update(link);
            botClient.linkUpdate(LinkUpdateRequest.builder()
                    .id(link.getId())
                    .description("Github repository update")
                    .url(link.getUrl().toString())
                    .tgChatIds(chatIds)
                    .build());
            return chatIds.size();
        }
        return 0;
    }
}
