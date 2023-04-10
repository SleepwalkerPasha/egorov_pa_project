package ru.tinkoff.edu.java.bot.service.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import ru.tinkoff.edu.java.bot.service.commands.Command;

import java.util.List;

public interface UserMessageProcessor {

    List<? extends Command> commands();

    SendMessage process(Update update);
}
