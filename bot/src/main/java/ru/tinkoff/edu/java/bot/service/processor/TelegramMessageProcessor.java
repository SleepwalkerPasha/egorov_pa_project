package ru.tinkoff.edu.java.bot.service.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.bot.service.commands.*;
import ru.tinkoff.edu.java.bot.storage.LinkStorage;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TelegramMessageProcessor implements UserMessageProcessor {

    private final Map<String, Command> commands;

    private final Pattern pattern;

    public TelegramMessageProcessor(LinkStorage storage) {
        HelpCommand helpCommand = new HelpCommand();

        this.commands = Stream.of(new StartCommand(storage),
                helpCommand,
                new TrackCommand(storage),
                new UntrackCommand(storage),
                new ListCommand(storage)).collect(Collectors.toMap(Command::command, Function.identity()));

        helpCommand.setInfo(buildHelpString());

        pattern = Pattern.compile("^/[a-zA-Z]+$");
    }

    @Override
    public List<? extends Command> commands() {
        return commands.values().stream().toList();
    }

    @Override
    public SendMessage process(Update update) {
        Command command = findCommand(update);
        if (command == null)
            return new SendMessage(update.message().chat().id(), "Команда не поддерживается");
        else
            return command.handle(update);
    }

    private String buildHelpString() {
        return commands.values()
                .stream()
                .map(c -> c.command() + " - " + c.description())
                .collect(Collectors.joining("\n", "Команды бота: \n", ""));
    }

    private Command findCommand(Update update) {
        String botCommandAndArgs = update.message().text();
        String botCommand = botCommandAndArgs;
        if (botCommandAndArgs.contains(" "))
            botCommand = botCommandAndArgs.split(" ")[0];
        Matcher matcher = pattern.matcher(botCommand);
        if (matcher.matches())
            return commands.get(botCommand);
        else
            return null;
    }
}
