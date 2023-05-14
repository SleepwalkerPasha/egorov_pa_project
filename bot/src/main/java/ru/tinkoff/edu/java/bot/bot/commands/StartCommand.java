package ru.tinkoff.edu.java.bot.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.vdurmont.emoji.EmojiParser;
import java.util.HashSet;
import ru.tinkoff.edu.java.bot.storage.LinkStorage;


public class StartCommand implements Command {

    private static final String TEXT = "Приветствую, это бот для отслеживания"
        + " обновлений репозиториев Github и вопросов StackOverflow! :wave:";
    private final LinkStorage storage;

    public StartCommand(LinkStorage storage) {
        this.storage = storage;
    }

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "зарегистрировать пользователя";
    }

    @Override
    public SendMessage handle(Update update) {
        String answer = EmojiParser.parseToUnicode(TEXT);
        Long chatId = update.message().chat().id();
        storage.registerUser(chatId, new HashSet<>());
        return new SendMessage(chatId, answer);
    }
}
