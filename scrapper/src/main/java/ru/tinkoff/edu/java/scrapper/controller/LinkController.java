package ru.tinkoff.edu.java.scrapper.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.client.GithubClient;
import ru.tinkoff.edu.java.scrapper.client.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.dto.db.Link;
import ru.tinkoff.edu.java.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.ListLinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.Response;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.service.LinkService;
import ru.tinkoff.edu.parser.AbstractParser;
import ru.tinkoff.edu.parser.dto.GithubResult;
import ru.tinkoff.edu.parser.dto.ParseResult;
import ru.tinkoff.edu.parser.dto.StackOverflowResult;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    private final AbstractParser parser;

    private final GithubClient githubClient;

    private final StackOverflowClient stackOverflowClient;

    @GetMapping("/links")
    public ListLinkResponse getFollowedLinks(@RequestHeader("Tg-Chat-Id") Long id) {
        List<LinkResponse> links = linkService.listAllLinksById(id)
                .stream()
                .map(x -> new LinkResponse(x.getId(), x.getUrl().toString()))
                .toList();
        return new ListLinkResponse(links, (long) links.size());
    }

    @PostMapping("/links")
    public LinkResponse addLinkToFollowing(@RequestHeader("Tg-Chat-Id") Long id, @RequestBody @Valid AddLinkRequest link) {
        ParseResult parse = parser.parse(URI.create(link.link()));
        Link added;
        if (parse.resultType().equals("GithubResult")) {
            GithubResult result = (GithubResult) parse;
            Response githubResponse = githubClient.fetchRepository(result.name(), result.repoName());
            added = linkService.add(id, link, githubResponse);
        } else if (parse.resultType().equals("StackOverflowResult")) {
            StackOverflowResult stackOverflowResult = (StackOverflowResult) parse;
            Response stackOverflowResponse = stackOverflowClient.fetchQuestion(stackOverflowResult.id().toString());
            added = linkService.add(id, link, stackOverflowResponse);
        } else
            throw new BadRequestException("данные ссылки не поддерживаются");
        return new LinkResponse(added.getTgId(), added.getUrl().toString());
    }

    @DeleteMapping("/links")
    public LinkResponse removeLinkFromFollowing(@RequestHeader("Tg-Chat-Id") Long id, @RequestBody @Valid RemoveLinkRequest link) {
        Link removed = linkService.remove(id, link);
        return new LinkResponse(removed.getTgId(), removed.getUrl().toString());
    }
}
