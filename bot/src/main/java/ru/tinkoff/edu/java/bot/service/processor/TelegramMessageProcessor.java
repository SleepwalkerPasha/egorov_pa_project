package ru.tinkoff.edu.java.bot.service.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.bot.service.commands.*;
import ru.tinkoff.edu.java.bot.storage.LinkStorage;

import java.util.List;
import java.util.stream.Collectors;

public class TelegramMessageProcessor implements UserMessageProcessor {

    private final List<Command> commands;

    private final LinkStorage storage;

    public TelegramMessageProcessor(LinkStorage storage) {
        this.storage = storage;
        HelpCommand helpCommand = new HelpCommand();

        this.commands = List.of(new StartCommand(storage),
                helpCommand,
                new TrackCommand(storage),
                new UntrackCommand(storage),
                new ListCommand(storage));

        helpCommand.setInfo(buildHelpString());
    }

    @Override
    public List<? extends Command> commands() {
        return commands;
    }

    @Override
    public SendMessage process(Update update) {
        return findCommand(update).handle(update);
    }

    public Command findCommand(Update update) {
        return commands.parallelStream()
                .filter(c -> c.supports(update))
                .findAny()
                .orElse(new UnknownCommand());
    }

    private String buildHelpString() {
        return commands
                .stream()
                .map(c -> c.command() + " - " + c.description())
                .collect(Collectors.joining("\n", "Команды бота: \n", ""));
    }
}
