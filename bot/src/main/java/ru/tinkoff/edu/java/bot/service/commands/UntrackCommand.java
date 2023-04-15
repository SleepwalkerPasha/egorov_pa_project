package ru.tinkoff.edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.bot.storage.LinkStorage;

public class UntrackCommand implements Command{

    private final LinkStorage storage;

    public UntrackCommand(LinkStorage storage) {
        this.storage = storage;
    }


    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "прекратить отслеживание ссылки\nвведите команду в формате: " + command() + " url";
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        String[] split = update.message().text().split(" ");
        if (split.length == 1)
            return new SendMessage(chatId, description());
        String url = split[1];
        storage.untrackLink(chatId, url);
        return new SendMessage(chatId, "Ссылка больше не отслеживается");
    }
}
