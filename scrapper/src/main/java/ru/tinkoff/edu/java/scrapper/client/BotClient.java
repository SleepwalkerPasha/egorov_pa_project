package ru.tinkoff.edu.java.scrapper.client;

import ru.tinkoff.edu.java.scrapper.dto.request.LinkUpdateRequest;

public interface BotClient {

    void linkUpdate(LinkUpdateRequest request);
}
