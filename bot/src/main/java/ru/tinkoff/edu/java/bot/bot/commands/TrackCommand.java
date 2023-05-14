package ru.tinkoff.edu.java.bot.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.bot.storage.LinkStorage;

public class TrackCommand implements Command {

    private final LinkStorage storage;

    public TrackCommand(LinkStorage storage) {
        this.storage = storage;
    }

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "начать отслеживание ссылки\nвведите команду в формате: " + command() + " url";
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        String[] split = update.message().text().split(" ");
        if (split.length == 1) {
            return new SendMessage(chatId, description());
        }
        String url = split[1];
        storage.trackLink(chatId, url);
        return new SendMessage(chatId, "Ссылка теперь отслеживается");
    }
}
