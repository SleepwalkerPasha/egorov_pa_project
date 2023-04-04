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
import ru.tinkoff.edu.java.bot.service.commands.ListCommand;
import ru.tinkoff.edu.java.bot.service.commands.UnknownCommand;
import ru.tinkoff.edu.java.bot.storage.InMemoryLinkStorage;
import ru.tinkoff.edu.java.bot.storage.LinkStorage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TelegramMessageProcessorTest {

    @Mock
    @InjectMocks
    TelegramMessageProcessor processor;

    Update update;

    @Mock
    LinkStorage storage;

    Chat chat;

    Message message;

    @BeforeEach
    void setUp() {
        processor = Mockito.mock(TelegramMessageProcessor.class);
        storage = Mockito.mock(InMemoryLinkStorage.class);
        update = new Update();
        message = new Message();
        chat = new Chat();
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
        ReflectionTestUtils.setField(message, "text", "/list");
        ReflectionTestUtils.setField(update, "message", message);

        when(processor.process(update)).thenReturn(new SendMessage(1L, ""));
        Command answer = processor.findCommand(update);

        assertEquals(ListCommand.class, answer.getClass());
    }

    @Test
    void shouldReturnMessageIfListIsEmpty() {

    }
}