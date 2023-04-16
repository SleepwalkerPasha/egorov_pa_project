package ru.tinkoff.edu.java.bot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import ru.tinkoff.edu.java.bot.bot.commands.Command;
import ru.tinkoff.edu.java.bot.bot.processor.UserMessageProcessor;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
public class TelegramRefresherBot implements Bot {

    private TelegramBot bot;

    private final String token;

    private final List<? extends Command> commands;

    private final UserMessageProcessor processor;

    public TelegramRefresherBot(String token, UserMessageProcessor processor) {
        this.token = token;
        this.processor = processor;
        commands = processor.commands();
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        updates.stream()
                .peek(this::printUpdate)
                .map(this::processUpdate)
                .forEach(this::sendMessage);
        return CONFIRMED_UPDATES_ALL;
    }

    private SendMessage processUpdate(Update update) {
        return processor.process(update);
    }


    @Override
    public void start() {
        if (bot == null) {
            bot = new TelegramBot(token);

            this.execute(new SetMyCommands(commands
                    .stream()
                    .map(Command::toApiCommand)
                    .toArray(BotCommand[]::new)));

            bot.setUpdatesListener(this);
        }
    }

    @Override
    public void close() {
        if (bot != null)
            bot.removeGetUpdatesListener();
    }

    private void sendMessage(SendMessage sendMessage) {
        if (bot != null)
            bot.execute(sendMessage);
    }

    private void printUpdate(Update update) {
        log.info("Tg-Bot chatId = {}, updateId = {}, text = {}, time = {}\n",
                update.message().chat().id(), update.updateId(), update.message().text(), OffsetDateTime.now());
    }

}
