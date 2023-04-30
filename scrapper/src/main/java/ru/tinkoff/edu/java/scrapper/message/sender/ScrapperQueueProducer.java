package ru.tinkoff.edu.java.scrapper.message.sender;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ru.tinkoff.edu.java.common.dto.request.LinkUpdateRequest;


@RequiredArgsConstructor
public class ScrapperQueueProducer implements LinkUpdateSender {

    private final RabbitTemplate template;

    private final String exchangeName;

    private final String routingKey;

    @Override
    public void linkUpdate(LinkUpdateRequest request) {
        template.convertAndSend(exchangeName, routingKey, request);
    }

}
