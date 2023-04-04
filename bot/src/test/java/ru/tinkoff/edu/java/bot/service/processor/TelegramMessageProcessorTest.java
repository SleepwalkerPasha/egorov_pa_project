package ru.tinkoff.edu.java.bot.service.processor;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import ru.tinkoff.edu.java.bot.service.commands.Command;
import ru.tinkoff.edu.java.bot.service.commands.UnknownCommand;
import ru.tinkoff.edu.java.bot.storage.InMemoryLinkStorage;
import ru.tinkoff.edu.java.bot.storage.LinkStorage;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TelegramMessageProcessorTest {

    @Mock
    @InjectMocks
    TelegramMessageProcessor processor;

    Update update;

    Chat chat;

    Message message;

    @Mock
    LinkStorage storage;

    @BeforeEach
    void setUp() {
        processor = Mockito.mock(TelegramMessageProcessor.class);
        update = new Update();
        message = new Message();
        chat = new Chat();
        storage = Mockito.mock(InMemoryLinkStorage.class);
        ReflectionTestUtils.setField(chat, "id", 1L);
    }

    @Test
    void shouldReturnUnknownCommandMessage() {
        ReflectionTestUtils.setField(message, "chat", chat);
        ReflectionTestUtils.setField(message, "text", "/something");
        ReflectionTestUtils.setField(update, "message", message);
        when(processor.findCommand(update)).thenReturn(new UnknownCommand());
        Command answer = processor.findCommand(update);

        assertEquals(UnknownCommand.class, answer.getClass());
    }

    @Test
    void shouldReturnListSuccess() {
        ReflectionTestUtils.setField(message, "chat", chat);
        ReflectionTestUtils.setField(message, "text", "/lists");
        ReflectionTestUtils.setField(update, "message", message);
        Map<Long, Set<String>> map = new ConcurrentHashMap<>();
        map.put(1L, Set.of("https://github.com/SleepwalkerPasha/java-filmorate"));
        ReflectionTestUtils.setField(storage, "map", map);
        ReflectionTestUtils.setField(processor, "storage", storage);

        String text = "Список отслеживаемых ссылок: \n" + String.join("\n", map.get(1L));

        SendMessage expected = new SendMessage(1L, text);

        when(processor.process(update)).thenReturn(expected);

        SendMessage answer = processor.process(update);

        assertEquals(expected, answer);
    }

    @Test
    void shouldReturnMessageIfListIsEmpty() {
        ReflectionTestUtils.setField(message, "chat", chat);
        ReflectionTestUtils.setField(message, "text", "/lists");
        ReflectionTestUtils.setField(update, "message", message);
        Map<Long, Set<String>> map = new ConcurrentHashMap<>();
        map.put(1L, new HashSet<>());
        ReflectionTestUtils.setField(storage, "map", map);
        ReflectionTestUtils.setField(processor, "storage", storage);

        SendMessage expected = new SendMessage(1L, "Список отслеживаемых ссылок пуст");

        when(processor.process(update)).thenReturn(expected);

        SendMessage answer = processor.process(update);

        assertEquals(expected, answer);
    }

}