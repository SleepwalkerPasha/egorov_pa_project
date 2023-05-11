package ru.tinkoff.edu.java.bot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import ru.tinkoff.edu.java.common.dto.request.LinkUpdateRequest;

@RabbitListener(queues = "${app.queue-name}")
@Slf4j
@RequiredArgsConstructor
public class ScrapperQueueListener implements Listener {

    private final UpdatesHandler handler;

    @Override
    @RabbitHandler
    public void receiveNotification(LinkUpdateRequest request) {
        handler.handleUpdates(request);
    }

    @RabbitListener(queues = "${app.queue-name}" + ".dlq")
    public void processFailedMessages(Message message) {
        log.info("Received failed message: {}", message.toString());
    }

}
