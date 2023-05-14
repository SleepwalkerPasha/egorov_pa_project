package ru.tinkoff.edu.java.bot.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import ru.tinkoff.edu.java.bot.bot.commands.Command;


public interface UserMessageProcessor {

    List<? extends Command> commands();

    SendMessage process(Update update);
}
