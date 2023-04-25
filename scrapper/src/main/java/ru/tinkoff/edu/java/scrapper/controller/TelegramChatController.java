package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@RestController
public class TelegramChatController {

    private final TgChatService service;

    public TelegramChatController(@Qualifier("JdbcTgChatService") TgChatService service) {
        this.service = service;
    }

    @PostMapping("/tg-chat/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void registerNewChat(@PathVariable Long id) {
        service.register(id);
    }

    @DeleteMapping("/tg-chat/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteChat(@PathVariable Long id) {
        service.unregister(id);
    }

}
