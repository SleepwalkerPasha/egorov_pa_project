package ru.tinkoff.edu.java.bot.configuration.listener.access.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.bot.service.Listener;
import ru.tinkoff.edu.java.bot.service.ScrapperQueueListener;
import ru.tinkoff.edu.java.bot.service.UpdatesHandler;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
@RequiredArgsConstructor
public class RabbitMQConfiguration {

    @Bean
    public Queue transferQueue(ApplicationConfig applicationConfig) {
        return QueueBuilder.durable(applicationConfig.queueName())
            .withArgument("x-dead-letter-exchange", applicationConfig.dlxName())
            .build();
    }

    @Bean
    public Queue deadLetterQueue(ApplicationConfig applicationConfig) {
        return QueueBuilder.durable(applicationConfig.dlqName()).build();
    }

    @Bean
    public DirectExchange transferDirectExchange(ApplicationConfig applicationConfig) {
        return new DirectExchange(applicationConfig.exchangeName(), true, false);
    }

    @Bean
    public FanoutExchange deadLetterExchange(ApplicationConfig applicationConfig) {
        return new FanoutExchange(applicationConfig.dlqName());
    }

    @Bean
    public Binding transferBinding(DirectExchange directExchange, ApplicationConfig applicationConfig) {
        return BindingBuilder.bind(transferQueue(applicationConfig)).to(directExchange)
            .with(applicationConfig.routingKey());
    }

    @Bean
    public Binding deadLetterBinding(ApplicationConfig applicationConfig, FanoutExchange exchange) {
        return BindingBuilder.bind(deadLetterQueue(applicationConfig)).to(exchange);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Listener transferReceiver(UpdatesHandler updatesHandler) {
        return new ScrapperQueueListener(updatesHandler);
    }

}
