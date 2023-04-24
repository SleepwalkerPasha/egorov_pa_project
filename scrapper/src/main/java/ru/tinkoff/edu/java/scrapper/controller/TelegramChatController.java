package ru.tinkoff.edu.java.scrapper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@RestController
@RequiredArgsConstructor
public class TelegramChatController {

    private final TgChatService service;

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
