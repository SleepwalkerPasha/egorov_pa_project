package ru.tinkoff.edu.java.bot.service;

import ru.tinkoff.edu.java.common.dto.request.LinkUpdateRequest;

public interface Listener {

    void receiveNotification(LinkUpdateRequest request);
}
