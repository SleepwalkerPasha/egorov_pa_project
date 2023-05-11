package ru.tinkoff.edu.java.scrapper.message.sender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ru.tinkoff.edu.java.common.dto.request.LinkUpdateRequest;


@Slf4j
@RequiredArgsConstructor
public class ScrapperQueueProducer implements LinkUpdateSender {

    private final RabbitTemplate template;

    private final String exchangeName;

    private final String routingKey;

    @Override
    public void linkUpdate(LinkUpdateRequest request) {
        log.info("отправили данные о полученном обновлении");
        template.convertAndSend(exchangeName, routingKey, request);
    }

}
