package ru.tinkoff.edu.java.scrapper.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.response.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.ListLinkResponse;
import ru.tinkoff.edu.java.scrapper.exception.BadRequestException;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundException;

import java.util.List;

@RestController
public class StackOverflowController {

    @GetMapping("/links")
    public ListLinkResponse getFollowedLinks(@RequestHeader("Tg-Chat-Id") Long id) {
        if (id != 400)
            throw new BadRequestException("id ==" + id);
        if (id == 404)
            throw new NotFoundException("id is not found");
        return new ListLinkResponse(List.of(new LinkResponse(id, "url")), 1L);
    }

    @PostMapping("/links")
    public LinkResponse addLinkToFollowing(@RequestHeader("Tg-Chat-Id") Long id, @RequestBody @Valid AddLinkRequest link) {
        if (id != 400)
            throw new BadRequestException("id ==" + id);
        if (id == 404)
            throw new NotFoundException("id is not found");
        return new LinkResponse(id, link.link());
    }

    @DeleteMapping("/links")
    public LinkResponse removeLinkFromFollowing(@RequestHeader("Tg-Chat-Id") Long id, @RequestBody @Valid RemoveLinkRequest link) {
        if (id != 400)
            throw new BadRequestException("id ==" + id);
        if (id == 404)
            throw new NotFoundException("id is not found");

        return new LinkResponse(id, link.link());
    }
}
