package ru.tinkoff.edu.java.scrapper.repository;

import java.util.Collection;
import java.util.Optional;

public interface TgChatRepository {

    long add(long chatId);

    long remove(long chatId);

    Collection<Long> findAll();

    Optional<Long> findById(long id);
}
