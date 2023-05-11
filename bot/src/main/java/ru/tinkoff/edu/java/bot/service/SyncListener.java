package ru.tinkoff.edu.java.bot.service;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.common.dto.request.LinkUpdateRequest;


@RequiredArgsConstructor
public class SyncListener implements Listener {

    private final UpdatesHandler handler;

    @Override
    public void receiveNotification(LinkUpdateRequest request) {
        handler.handleUpdates(request);
    }
}
