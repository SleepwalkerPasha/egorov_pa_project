package ru.tinkoff.edu.java.scrapper.service.jdbc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@Service
public class JdbcTgChatService implements TgChatService {

    private final TgChatRepository tgChatRepository;

    public JdbcTgChatService(@Qualifier("JdbcTgChatRepository") TgChatRepository tgChatRepository) {
        this.tgChatRepository = tgChatRepository;
    }

    @Override
    public void register(long tgChatId) {
        tgChatRepository.register(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        tgChatRepository.unregister(tgChatId);
    }
}
