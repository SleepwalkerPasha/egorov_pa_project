package ru.tinkoff.edu.java.bot.bot.processor;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import ru.tinkoff.edu.java.bot.bot.commands.Command;
import ru.tinkoff.edu.java.bot.bot.commands.HelpCommand;
import ru.tinkoff.edu.java.bot.bot.commands.ListCommand;
import ru.tinkoff.edu.java.bot.bot.commands.StartCommand;
import ru.tinkoff.edu.java.bot.bot.commands.TrackCommand;
import ru.tinkoff.edu.java.bot.bot.commands.UnknownCommand;
import ru.tinkoff.edu.java.bot.bot.commands.UntrackCommand;
import ru.tinkoff.edu.java.bot.storage.InMemoryLinkStorage;
import ru.tinkoff.edu.java.bot.storage.LinkStorage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TelegramMessageProcessorTest {

    @InjectMocks
    TelegramMessageProcessor processor;
    Update update;
    Chat chat;
    Message message;
    @Mock
    LinkStorage storage;

    @BeforeEach
    void setUp() {
        storage = Mockito.mock(InMemoryLinkStorage.class);
        processor = new TelegramMessageProcessor();
        update = new Update();
        message = new Message();
        chat = new Chat();
        ReflectionTestUtils.setField(chat, "id", 1L);
        HelpCommand helpCommand = new HelpCommand();
        List<Command> commands = new ArrayList<>() {{
            add(new StartCommand(storage));
            add(new TrackCommand(storage));
            add(new UntrackCommand(storage));
            add(new ListCommand(storage));
        }};
        helpCommand.setInfo(commands);
        ReflectionTestUtils.setField(processor, "commands", commands);
        ReflectionTestUtils.setField(processor, "helpCommand", helpCommand);
    }

    @Test
    void shouldReturnUnknownCommandMessage() {
        ReflectionTestUtils.setField(message, "chat", chat);
        ReflectionTestUtils.setField(message, "text", "/something");
        ReflectionTestUtils.setField(update, "message", message);
        Command answer = processor.findCommand(update);

        assertEquals(UnknownCommand.class, answer.getClass());
    }

    @Test
    void shouldReturnListSuccess() {
        ReflectionTestUtils.setField(message, "chat", chat);
        ReflectionTestUtils.setField(message, "text", "/lists");
        ReflectionTestUtils.setField(update, "message", message);
        Set<String> set = Set.of("https://github.com/SleepwalkerPasha/java-filmorate");
        String text = "Список отслеживаемых ссылок: \n" + String.join("\n", set);

        when(storage.getFollowedLinks(1L)).thenReturn(set);

        SendMessage expected = new SendMessage(1L, text);

        SendMessage answer = processor.process(update);

        assertEquals(expected.getParameters().get("text"), answer.getParameters().get("text"));
    }

    @Test
    void shouldReturnMessageIfListIsEmpty() {
        ReflectionTestUtils.setField(message, "chat", chat);
        ReflectionTestUtils.setField(message, "text", "/lists");
        ReflectionTestUtils.setField(update, "message", message);
        String text = "Список отслеживаемых ссылок пуст";

        when(storage.getFollowedLinks(1L)).thenReturn(new HashSet<>());

        SendMessage expected = new SendMessage(1L, text);

        SendMessage answer = processor.process(update);

        assertEquals(expected.getParameters().get("text"), answer.getParameters().get("text"));
    }

}
