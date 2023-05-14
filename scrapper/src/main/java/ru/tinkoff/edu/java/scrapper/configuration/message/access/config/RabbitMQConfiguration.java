package ru.tinkoff.edu.java.scrapper.configuration.message.access.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfigProperties;
import ru.tinkoff.edu.java.scrapper.message.sender.LinkUpdateSender;
import ru.tinkoff.edu.java.scrapper.message.sender.ScrapperQueueProducer;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
@RequiredArgsConstructor
public class RabbitMQConfiguration {

    private final ApplicationConfigProperties applicationConfig;

    @Bean
    public Queue transferQueue() {
        return QueueBuilder
            .durable(applicationConfig.queueName())
            .withArgument("x-dead-letter-exchange", applicationConfig.exchangeName() + ".dlx")
            .build();
    }

    @Bean
    public DirectExchange transferDirectExchange() {
        return new DirectExchange(applicationConfig.exchangeName(), true, false);
    }

    @Bean
    public Binding transferBinding() {
        return BindingBuilder.bind(transferQueue()).to(transferDirectExchange()).with(applicationConfig.routingKey());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public LinkUpdateSender transferUpdateService() {
        return new ScrapperQueueProducer(
            new RabbitTemplate(),
            applicationConfig.exchangeName(),
            applicationConfig.routingKey()
        );
    }

}
