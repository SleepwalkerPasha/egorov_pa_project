package ru.tinkoff.edu.java.scrapper.repository;

public interface TgChatRepository {

    void register(long chatId);

    void unregister(long chatId);

}
