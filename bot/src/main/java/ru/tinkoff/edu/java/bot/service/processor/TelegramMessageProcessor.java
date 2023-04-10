package ru.tinkoff.edu.java.bot.service.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.service.commands.Command;
import ru.tinkoff.edu.java.bot.service.commands.HelpCommand;
import ru.tinkoff.edu.java.bot.service.commands.UnknownCommand;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramMessageProcessor implements UserMessageProcessor {

    @Autowired(required = false)
    private final List<Command> commands = new ArrayList<>();

    @Autowired
    private HelpCommand helpCommand;


    private void initHelpCommand() {
        if (!commands.contains(helpCommand))
            commands.add(helpCommand);
        if (helpCommand.getInfo() == null)
            helpCommand.setInfo(commands);
    }

    @Override
    public List<? extends Command> commands() {
        initHelpCommand();
        return commands;
    }

    @Override
    public SendMessage process(Update update) {
        return findCommand(update).handle(update);
    }

    public Command findCommand(Update update) {
        initHelpCommand();
        return commands.parallelStream()
                .filter(c -> c.supports(update))
                .findAny()
                .orElse(new UnknownCommand());
    }

}
