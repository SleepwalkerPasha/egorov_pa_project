package ru.tinkoff.edu.java.bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import ru.tinkoff.edu.java.common.dto.request.LinkUpdateRequest;

@RabbitListener(queues = "${app.queue-name}")
@RequiredArgsConstructor
public class ScrapperQueueListener implements Listener {

    private final UpdatesHandler handler;

    @Override
    @RabbitHandler
    public void receiveNotification(LinkUpdateRequest request) {
        handler.handleUpdates(request);
    }

}
