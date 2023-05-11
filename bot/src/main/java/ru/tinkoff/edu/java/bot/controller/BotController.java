package ru.tinkoff.edu.java.bot.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.service.Listener;
import ru.tinkoff.edu.java.common.dto.request.LinkUpdateRequest;

@RestController
public class BotController {

    private final Listener service;

    @Autowired
    public BotController(Listener service) {
        this.service = service;
    }

    @PostMapping("/updates")
    public void linkUpdate(@RequestBody @NotNull @Valid LinkUpdateRequest request) {
        service.receiveNotification(request);
    }
}
