package ru.tinkoff.edu.java.scrapper.service.jooq;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@RequiredArgsConstructor
public class JooqTgChatService implements TgChatService {

    private final TgChatRepository repository;

    @Override
    public void register(long tgChatId) {
        repository.add(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        repository.remove(tgChatId);
    }
}
