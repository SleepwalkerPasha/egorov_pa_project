package ru.tinkoff.edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.bot.bot.Bot;
import ru.tinkoff.edu.java.common.dto.request.LinkUpdateRequest;


@RequiredArgsConstructor
public class UpdatesHandler {

    private final Bot bot;

    public void handleUpdates(LinkUpdateRequest request) {
        for (Long tgChatId : request.getTgChatIds()) {
            bot.execute(new SendMessage(tgChatId,
                String.format("Произошло обновление по ссылке: %s/n%s", request.getUrl(), request.getDescription())));
        }
    }
}
