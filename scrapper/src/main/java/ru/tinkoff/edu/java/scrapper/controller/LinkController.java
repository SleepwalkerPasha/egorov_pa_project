package ru.tinkoff.edu.java.scrapper.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.db.Link;
import ru.tinkoff.edu.java.scrapper.dto.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.ListLinkResponse;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.util.List;

@RestController
public class LinkController {

    private final LinkService linkService;

    public LinkController(@Qualifier("JdbcLinkService") LinkService linkService) {
        this.linkService = linkService;
    }

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
        Link added = linkService.add(id, link);
        return new LinkResponse(added.getTgId(), added.getUrl().toString());
    }

    @DeleteMapping("/links")
    public LinkResponse removeLinkFromFollowing(@RequestHeader("Tg-Chat-Id") Long id, @RequestBody @Valid RemoveLinkRequest link) {
        Link removed = linkService.remove(id, link);
        return new LinkResponse(removed.getTgId(), removed.getUrl().toString());
    }
}
