package ru.tinkoff.edu.java.bot.updater;

import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.bot.Bot;

@Service
public class UpdateService {

    private final Bot bot;

    public UpdateService(Bot bot) {
        this.bot = bot;
    }

    public void sendNotification(LinkUpdateRequest request) {
        for (Long tgChatId : request.getTgChatIds()) {
            bot.execute(new SendMessage(tgChatId,
                    String.format("Произошло обновление по ссылке: %s/n%s", request.getUrl(), request.getDescription())));
        }
    }
}
