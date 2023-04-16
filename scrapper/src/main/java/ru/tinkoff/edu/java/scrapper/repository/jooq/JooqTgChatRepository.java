package ru.tinkoff.edu.java.scrapper.repository.jooq;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.repository.TgChatRepository;

import java.util.Collection;
import java.util.Optional;

@Repository
@Qualifier("JooqTgChatRepository")
public class JooqTgChatRepository implements TgChatRepository {
    @Override
    public long add(long chatId) {
        return 0;
    }

    @Override
    public long remove(long chatId) {
        return 0;
    }

    @Override
    public Collection<Long> findAll() {
        return null;
    }

    @Override
    public Collection<Long> findByLink(long id) {
        return null;
    }

    @Override
    public Optional<Long> findByTgChatId(long id) {
        return Optional.empty();
    }
}
