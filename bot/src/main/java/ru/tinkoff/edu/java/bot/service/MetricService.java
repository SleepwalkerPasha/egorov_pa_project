package ru.tinkoff.edu.java.bot.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetricService {

    private static final String HANDLED_MESSAGES_TOTAL = "handle_message_total";

    private final Counter counter;

    @Autowired
    public MetricService(MeterRegistry registry) {
        counter = registry.counter(HANDLED_MESSAGES_TOTAL);
    }

    public void incrementHandleMessageCount() {
        counter.increment();
    }
}
