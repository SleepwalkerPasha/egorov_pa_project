package ru.tinkoff.edu.java.bot.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Set;
import ru.tinkoff.edu.java.bot.storage.LinkStorage;

public class ListCommand implements Command {

    private final LinkStorage storage;

    public ListCommand(LinkStorage storage) {
        this.storage = storage;
    }

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "показать список отслеживаемых ссылок";
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        return new SendMessage(chatId, formatLinkList(storage.getFollowedLinks(chatId)));
    }

    private String formatLinkList(Set<String> links) {
        if (links.isEmpty()) {
            return "Список отслеживаемых ссылок пуст";
        }
        return "Список отслеживаемых ссылок: \n" + String.join("\n", links);
    }
}
