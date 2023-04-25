package ru.tinkoff.edu.java.scrapper.service.jooq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@Service
@Qualifier("JooqTgChatService")
public class JooqTgChatService implements TgChatService {

    private final TgChatRepository repository;

    @Autowired
    public JooqTgChatService(@Qualifier("JooqTgChatRepository") TgChatRepository repository) {
        this.repository = repository;
    }


    @Override
    public void register(long tgChatId) {
        repository.add(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        repository.remove(tgChatId);
    }
}
