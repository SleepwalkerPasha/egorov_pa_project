package ru.tinkoff.edu.java.scrapper.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@Service
public class JpaTgChatService implements TgChatService {

    private final TgChatRepository tgChatRepository;

    @Autowired
    public JpaTgChatService(@Qualifier("JpaTgChatRepository") TgChatRepository tgChatRepository) {
        this.tgChatRepository = tgChatRepository;
    }

    @Override
    public void register(long tgChatId) {
        tgChatRepository.add(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        tgChatRepository.remove(tgChatId);
    }
}
