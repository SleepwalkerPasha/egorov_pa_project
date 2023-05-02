package ru.tinkoff.edu.java.scrapper.service.jpa;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;
import ru.tinkoff.edu.java.scrapper.service.TgChatService;

@RequiredArgsConstructor
public class JpaTgChatService implements TgChatService {

    private final TgChatRepository tgChatRepository;

    @Override
    public void register(long tgChatId) {
        tgChatRepository.add(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        tgChatRepository.remove(tgChatId);
    }
}
