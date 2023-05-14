package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.bot.commands.Command;
import ru.tinkoff.edu.java.bot.bot.commands.HelpCommand;
import ru.tinkoff.edu.java.bot.bot.commands.ListCommand;
import ru.tinkoff.edu.java.bot.bot.commands.StartCommand;
import ru.tinkoff.edu.java.bot.bot.commands.TrackCommand;
import ru.tinkoff.edu.java.bot.bot.commands.UntrackCommand;
import ru.tinkoff.edu.java.bot.storage.LinkStorage;

@Configuration
public class CommandConfig {

    private final LinkStorage storage;

    @Autowired
    public CommandConfig(LinkStorage storage) {
        this.storage = storage;
    }

    @Bean
    public Command transferStartCommand() {
        return new StartCommand(storage);
    }

    @Bean
    public Command transferTrackCommand() {
        return new TrackCommand(storage);
    }

    @Bean
    public Command transferUntrackCommand() {
        return new UntrackCommand(storage);
    }

    @Bean
    public Command transferListCommand() {
        return new ListCommand(storage);
    }

    @Bean
    public HelpCommand transferHelpCommand() {
        return new HelpCommand();
    }

}
