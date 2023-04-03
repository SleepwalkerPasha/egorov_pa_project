package ru.tinkoff.edu.java.bot.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.exception.ApiErrorException;

@RestController
public class BotController {

    @PostMapping("/updates")
    public void linkUpdate(@RequestBody @Valid LinkUpdateRequest request) {
        if (request.getId() != 1L) {
            throw new ApiErrorException("id == " + request.getId());
        }
    }
}
