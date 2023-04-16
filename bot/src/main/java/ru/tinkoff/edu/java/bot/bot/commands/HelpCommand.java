package ru.tinkoff.edu.java.bot.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;
import java.util.stream.Collectors;

public class HelpCommand implements Command {
    private String info;

    public HelpCommand() {
        super();
    }

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "вывести окно с командами";
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        return new SendMessage(chatId, info);
    }

    public String getInfo() {return info;}

    public void setInfo(List<Command> commands) {
        this.info = buildHelpString(commands);
    }


    private String buildHelpString(List<Command> commands) {
        return commands
                .stream()
                .map(c -> c.command() + " - " + c.description())
                .collect(Collectors.joining("\n", "Команды бота: \n", ""));
    }
}
